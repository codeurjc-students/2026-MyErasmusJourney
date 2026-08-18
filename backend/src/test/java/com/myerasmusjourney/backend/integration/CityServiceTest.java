package com.myerasmusjourney.backend.integration;

import com.myerasmusjourney.backend.TestDataBase;
import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.dto.*;
import com.myerasmusjourney.backend.mapper.CityMapper;
import com.myerasmusjourney.backend.repository.CityRepository;
import com.myerasmusjourney.backend.service.CityService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
public class CityServiceTest extends TestDataBase {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private CityService cityService;

    @BeforeEach
    void setup() {
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

        ResponseEntity<CityDTO> response = cityService.addCity(cityForm);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        CityDTO result = response.getBody();

        assertNotNull(result.id());
        assertEquals("Munich", result.name());
        assertEquals("Germany", result.country());
        assertEquals("A city in Germany", result.description());

        City savedCity = cityRepository.findById(result.id()).orElse(null);

        assertNotNull(savedCity);
        assertEquals("Munich", savedCity.getName());
        assertEquals("Germany", savedCity.getCountry());
        assertEquals("A city in Germany", savedCity.getDescription());

        assertNotNull(response.getHeaders().getLocation());
        assertEquals("/"+result.id(), response.getHeaders().getLocation().getPath());
    }

    @Test
    void testAddCityAlreadyExists() {
        City existingCity = cityRepository.save(new City("Munich", "Germany", "Existing description"));

        CityFormDTO cityForm = new CityFormDTO("Munich", "New description", "Germany");

        ResponseEntity<CityDTO> response = cityService.addCity(cityForm);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        CityDTO result = response.getBody();

        assertEquals(existingCity.getId(), result.id());
        assertEquals("Munich", result.name());
        assertEquals("Germany", result.country());
        assertEquals("Existing description", result.description());

        List<City> cities = cityRepository.findByName("Munich");

        assertEquals(1, cities.size());
        assertEquals(existingCity.getId(), cities.getFirst().getId());
    }

    @Test
    void testAddCitySameNameDifferentCountry() {
        City existingCity = cityRepository.save(new City("Munich", "Germany", "Munich in Germany"));

        CityFormDTO cityForm = new CityFormDTO("Munich", "Another Munich", "United States");

        ResponseEntity<CityDTO> response = cityService.addCity(cityForm);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        CityDTO result = response.getBody();

        assertNotNull(result.id());
        assertNotEquals(existingCity.getId(), result.id());

        assertEquals("Munich", result.name());
        assertEquals("United States", result.country());
        assertEquals("Another Munich", result.description());

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