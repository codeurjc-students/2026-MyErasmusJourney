package com.myerasmusjourney.backend.integration;

import com.myerasmusjourney.backend.TestDataBase;
import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.dto.*;
import com.myerasmusjourney.backend.repository.CityRepository;
import com.myerasmusjourney.backend.service.CityService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
public class CityServiceTest extends TestDataBase {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityService cityService;

    @BeforeEach
    void setup() {
        if(cityRepository.count() > 0) cityRepository.deleteAll();

        List<City> cities = List.of(
                new City("Madrid", "Spain", ""),
                new City("Rome", "Italy", ""),
                new City("Berlin", "Germany", "")
        );

        cityRepository.saveAll(cities);
    }

    @AfterEach
    void deleteCities() {
        cityRepository.deleteAll();
    }

    @Test
    void testAddCitySuccessfully() {
        CityFormDTO cityForm = new CityFormDTO("Munich", "A city in Germany", "Germany");

        CityService.CityResult result = cityService.addCity(cityForm);

        assertTrue(result.created());
        assertNotNull(result.city());

        CityDTO cityDTO = result.city();

        assertNotNull(cityDTO.id());
        assertEquals("Munich", cityDTO.name());
        assertEquals("Germany", cityDTO.country());
        assertEquals("A city in Germany", cityDTO.description());

        City savedCity = cityRepository.findById(cityDTO.id()).orElse(null);

        assertNotNull(savedCity);
        assertEquals("Munich", savedCity.getName());
        assertEquals("Germany", savedCity.getCountry());
        assertEquals("A city in Germany", savedCity.getDescription());
    }

    @Test
    void testAddCityAlreadyExists() {
        City existingCity = cityRepository.save(new City("Munich", "Germany", "Existing description"));

        CityFormDTO cityForm = new CityFormDTO("Munich", "New description", "Germany");

        CityService.CityResult result = cityService.addCity(cityForm);

        assertFalse(result.created());
        assertNotNull(result.city());

        CityDTO cityDTO = result.city();

        assertEquals(existingCity.getId(), cityDTO.id());
        assertEquals("Munich", cityDTO.name());
        assertEquals("Germany", cityDTO.country());
        assertEquals("Existing description", cityDTO.description());

        List<City> cities = cityRepository.findByName("Munich");

        assertEquals(1, cities.size());
        assertEquals(existingCity.getId(), cities.getFirst().getId());
    }

    @Test
    void testAddCitySameNameDifferentCountry() {
        City existingCity = cityRepository.save(new City("Munich", "Germany", "Munich in Germany"));

        CityFormDTO cityForm = new CityFormDTO("Munich", "Another Munich", "United States");

        CityService.CityResult result = cityService.addCity(cityForm);

        assertTrue(result.created());
        assertNotNull(result.city());

        CityDTO cityDTO = result.city();

        assertNotNull(cityDTO.id());
        assertNotEquals(existingCity.getId(), cityDTO.id());

        assertEquals("Munich", cityDTO.name());
        assertEquals("United States", cityDTO.country());
        assertEquals("Another Munich", cityDTO.description());

        List<City> cities = cityRepository.findByName("Munich");

        assertEquals(2, cities.size());

        City germanCity = cities.stream().filter(city -> city.getCountry().equals("Germany")).findFirst().orElse(null);

        City americanCity = cities.stream().filter(city -> city.getCountry().equals("United States")).findFirst().orElse(null);

        assertNotNull(germanCity);
        assertNotNull(americanCity);

        assertEquals("Munich in Germany", germanCity.getDescription());
        assertEquals("Another Munich", americanCity.getDescription());
    }

    @Test
    void testGetCities() {
        Collection<CitySimpleDTO> result = cityService.getCities();

        assertNotNull(result);
        assertEquals(3, result.size());

        List<CitySimpleDTO> cities = result.stream().toList();

        assertTrue(cities.stream()
                .anyMatch(city ->
                        city.name().equals("Madrid") &&
                                city.country().equals("Spain")));

        assertTrue(cities.stream()
                .anyMatch(city ->
                        city.name().equals("Rome") &&
                                city.country().equals("Italy")));

        assertTrue(cities.stream()
                .anyMatch(city ->
                        city.name().equals("Berlin") &&
                                city.country().equals("Germany")));
    }

    @Test
    void testGetCitiesEmpty() {
        cityRepository.deleteAll();

        Collection<CitySimpleDTO> result = cityService.getCities();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}