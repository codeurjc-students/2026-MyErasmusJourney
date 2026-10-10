package com.myerasmusjourney.backend.integration;

import com.myerasmusjourney.backend.TestDataBase;
import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.*;
import com.myerasmusjourney.backend.mapper.CityMapper;
import com.myerasmusjourney.backend.repository.CityRepository;
import com.myerasmusjourney.backend.repository.ExperienceRepository;
import com.myerasmusjourney.backend.service.CityService;
import com.myerasmusjourney.backend.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
public class CityServiceTest extends TestDataBase {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;

    @Autowired
    private CityMapper cityMapper;


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
    @Transactional
    void testTrendingCities(){
        Authentication authentication = new UsernamePasswordAuthenticationToken("test@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userService.getLoggedUser();

        City madrid = cityRepository.findByName("Madrid").getFirst();
        City berlin = cityRepository.findByName("Berlin").getFirst();
        City rome = cityRepository.findByName("Rome").getFirst();

        List<Experience> experiences = List.of(
                // Madrid
                new Experience(
                        "Good experience in Madrid.",
                        "Madrid experience 1",
                        7.0F,
                        LocalDate.now().minusDays(13),
                        List.of("Culture"),
                        madrid,
                        user
                ),
                new Experience(
                        "Madrid experience 2",
                        "Nice city and good atmosphere.",
                        8.0F,
                        LocalDate.now().minusDays(10),
                        List.of("Gastronomy"),
                        madrid,
                        user
                ),
                new Experience(
                        "Madrid experience 3",
                        "Very good experience in Madrid.",
                        9.0F,
                        LocalDate.now().minusDays(6),
                        List.of("Social_Events"),
                        madrid,
                        user
                ),
                new Experience(
                        "Madrid experience 4",
                        "Excellent experience in Madrid.",
                        10.0F,
                        LocalDate.now().minusDays(2),
                        List.of("Culture"),
                        madrid,
                        user
                ),

                // Rome
                new Experience(

                        "Rome experience 1",
                        "Nice experience in Rome.",
                        8.0F,
                        LocalDate.now().minusDays(12),
                        List.of("Culture"),
                        rome,
                        user
                ),
                new Experience(
                        "Rome experience 2",
                        "Good experience in Rome.",
                        7.0F,
                        LocalDate.now().minusDays(8),
                        List.of("Gastronomy"),
                        rome,
                        user
                ),
                new Experience(
                        "Rome experience 3",
                        "Average experience in Rome.",
                        6.0F,
                        LocalDate.now().minusDays(3),
                        List.of("Personal_Experience"),
                        rome,
                        user
                ),

                // Berlin
                new Experience(
                        "Berlin experience 1",
                        "Very good experience in Berlin.",
                        9.0F,
                        LocalDate.now().minusDays(11),
                        List.of("Culture"),
                        berlin,
                        user
                ),
                new Experience(
                        "Berlin experience 2",
                        "Good experience in Berlin.",
                        8.0F,
                        LocalDate.now().minusDays(4),
                        List.of("Transportation"),
                        berlin,
                        user
                )
        );

        experiences = experienceRepository.saveAll(experiences);

        cityService.addExperience(experiences.getFirst(), madrid);
        cityService.addExperience(experiences.get(1), madrid);
        cityService.addExperience(experiences.get(2), madrid);
        cityService.addExperience(experiences.get(3), madrid);
        cityService.addExperience(experiences.get(4), rome);
        cityService.addExperience(experiences.get(5), rome);
        cityService.addExperience(experiences.get(6), rome);
        cityService.addExperience(experiences.get(7), berlin);
        cityService.addExperience(experiences.get(8), berlin);

        List<CitySimpleDTO> expectedList = cityMapper.toSimpleDTOs(List.of(madrid, rome, berlin));

        List<CitySimpleDTO> resultList = cityService.getTrendingCities();

        assertEquals(expectedList.size(), resultList.size());

        for(int i = 0; i < resultList.size(); i++){
            assertEquals(expectedList.get(i), resultList.get(i));
        }
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

    @Test
    @Transactional
    void testGetCity(){
        List<City> cities = cityRepository.findAll();

        City city = cities.getFirst();
        Double rating = cityRepository.findAverageRatingByCityId(city.getId());

        CityDTO expected = cityMapper.toDTO(city, rating);

        CityDTO result = cityService.getCity(city.getId());

        assertEquals(expected, result);
    }

    @Test
    void testGetCityNotFound(){
        Long id = 0L;

        assertThrows(NoSuchElementException.class, () -> cityService.getCity(id));
    }
}