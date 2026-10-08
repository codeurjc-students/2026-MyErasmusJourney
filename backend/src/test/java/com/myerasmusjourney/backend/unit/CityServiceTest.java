package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.dto.CityDTO;
import com.myerasmusjourney.backend.dto.CityFormDTO;
import com.myerasmusjourney.backend.dto.CitySimpleDTO;
import com.myerasmusjourney.backend.mapper.CityMapper;
import com.myerasmusjourney.backend.repository.CityRepository;
import com.myerasmusjourney.backend.service.CityService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
public class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CityMapper cityMapper;

    @InjectMocks
    private CityService cityService;

    @Test
    void testAddCitySuccessfully() {
        CityFormDTO cityForm = new CityFormDTO("Munich", "Germany", "A city in Germany");

        City savedCity = new City("Munich", "Germany", "A city in Germany");
        savedCity.setId(1L);

        CityDTO expectedDTO = new CityDTO(1L, "Munich", "Germany", "A city in Germany", List.of());

        when(cityRepository.findByName("Munich")).thenReturn(List.of());
        when(cityRepository.save(any(City.class))).thenReturn(savedCity);
        when(cityMapper.toDTO(savedCity)).thenReturn(expectedDTO);

        CityService.CityResult result = cityService.addCity(cityForm);

        assertTrue(result.created());
        assertNotNull(result.city());

        verify(cityRepository).findByName("Munich");
        verify(cityRepository).save(any(City.class));
        verify(cityMapper).toDTO(savedCity);
    }

    @Test
    void testAddCityAlreadyExists() {
        CityFormDTO cityForm = new CityFormDTO("Munich","A city in Germany", "Germany");

        City existingCity = new City("Munich","Germany","Existing description");
        existingCity.setId(1L);

        CityDTO expectedDTO = new CityDTO(1L,"Munich","Existing description", "Germany", List.of());

        when(cityRepository.findByName("Munich")).thenReturn(List.of(existingCity));

        when(cityMapper.toDTO(existingCity)).thenReturn(expectedDTO);

        CityService.CityResult result = cityService.addCity(cityForm);

        assertFalse(result.created());
        assertNotNull(result.city());

        verify(cityRepository).findByName("Munich");
        verify(cityMapper).toDTO(existingCity);

        verify(cityRepository, never()).save(any(City.class));
    }

    @Test
    void testAddCitySameNameDifferentCountry() {
        CityFormDTO cityForm = new CityFormDTO("Munich", "United States", "Another Munich"
        );

        City existingCity = new City("Munich", "Germany", "Munich in Germany");
        existingCity.setId(1L);

        City savedCity = new City("Munich", "United States", "Another Munich");
        savedCity.setId(2L);

        CityDTO expectedDTO = new CityDTO(2L, "Munich", "United States", "Another Munich", List.of());

        when(cityRepository.findByName("Munich")).thenReturn(List.of(existingCity));

        when(cityRepository.save(any(City.class))).thenReturn(savedCity);

        when(cityMapper.toDTO(savedCity)).thenReturn(expectedDTO);



        CityService.CityResult result = cityService.addCity(cityForm);

        assertTrue(result.created());
        assertNotNull(result.city());
        assertEquals(expectedDTO, result.city());

        verify(cityRepository).findByName("Munich");
        verify(cityRepository).save(any(City.class));
        verify(cityMapper).toDTO(savedCity);
    }

    @Test
    void testGetTrendingCities(){

        User user = new User();

        List<City> cities = List.of(
                new City("Madrid", "Spain", ""),
                new City("Rome", "Italy", ""),
                new City("Berlin", "Germany", "")
        );

        List<Experience> experiences = List.of(
                // Madrid
                new Experience(
                        "Good experience in Madrid.",
                        "Madrid experience 1",
                        7.0F,
                        LocalDate.now().minusDays(13),
                        List.of("Culture"),
                        cities.getFirst(),
                        user
                ),
                new Experience(
                        "Madrid experience 2",
                        "Nice city and good atmosphere.",
                        8.0F,
                        LocalDate.now().minusDays(10),
                        List.of("Gastronomy"),
                        cities.getFirst(),
                        user
                ),
                new Experience(
                        "Madrid experience 3",
                        "Very good experience in Madrid.",
                        9.0F,
                        LocalDate.now().minusDays(6),
                        List.of("Social_Events"),
                        cities.getFirst(),
                        user
                ),
                new Experience(
                        "Madrid experience 4",
                        "Excellent experience in Madrid.",
                        10.0F,
                        LocalDate.now().minusDays(2),
                        List.of("Culture"),
                        cities.getFirst(),
                        user
                ),

                // Rome
                new Experience(

                        "Rome experience 1",
                        "Nice experience in Rome.",
                        8.0F,
                        LocalDate.now().minusDays(12),
                        List.of("Culture"),
                        cities.get(1),
                        user
                ),
                new Experience(
                        "Rome experience 2",
                        "Good experience in Rome.",
                        7.0F,
                        LocalDate.now().minusDays(8),
                        List.of("Gastronomy"),
                        cities.get(1),
                        user
                ),
                new Experience(
                        "Rome experience 3",
                        "Average experience in Rome.",
                        6.0F,
                        LocalDate.now().minusDays(3),
                        List.of("Personal_Experience"),
                        cities.get(1),
                        user
                ),

                // Berlin
                new Experience(
                        "Berlin experience 1",
                        "Very good experience in Berlin.",
                        9.0F,
                        LocalDate.now().minusDays(11),
                        List.of("Culture"),
                        cities.get(2),
                        user
                ),
                new Experience(
                        "Berlin experience 2",
                        "Good experience in Berlin.",
                        8.0F,
                        LocalDate.now().minusDays(4),
                        List.of("Transportation"),
                        cities.get(2),
                        user
                )
        );

        List<CitySimpleDTO> expected = List.of(
                new CitySimpleDTO(null, "Madrid", "", "Spain"),
                new CitySimpleDTO(null, "Rome", "", "Italy"),
                new CitySimpleDTO(null, "Berlin", "", "Germany")
        );

        when(cityRepository.findAll()).thenReturn(cities);
        when(cityRepository.findRecentExperiencesOfCity(any(String.class), any(String.class), any(LocalDate.class), any(LocalDate.class))).thenReturn(experiences);
        when (cityMapper.toSimpleDTOs(cities)).thenReturn(expected);

        List<CitySimpleDTO> result = cityService.getTrendingCities();

        assertEquals(expected.size(), result.size());
        for(int i = 0; i < expected.size(); i++){
            assertEquals(expected.get(i), result.get(i));
        }
    }

    @Test
    void testGetCities() {
        List<City> cities = List.of(
                new City("Madrid", "Spain", "Madrid description"),
                new City("Rome", "Italy", "Rome description"),
                new City("Berlin", "Germany", "Berlin description")
        );

        List<CitySimpleDTO> expected = List.of(
                new CitySimpleDTO(null, "Madrid", "", "Spain"),
                new CitySimpleDTO(null, "Rome","",  "Italy"),
                new CitySimpleDTO(null, "Berlin", "", "Germany")
        );

        when(cityRepository.findAll()).thenReturn(cities);
        when(cityMapper.toSimpleDTOs(cities)).thenReturn(expected);

        Collection<CitySimpleDTO> result = cityService.getCities();

        assertNotNull(result);
        assertEquals(expected, result);

        verify(cityRepository).findAll();
        verify(cityMapper).toSimpleDTOs(cities);
    }

    @Test
    void testGetCitiesEmpty() {
        List<City> cities = List.of();
        List<CitySimpleDTO> expected = List.of();

        when(cityRepository.findAll()).thenReturn(cities);
        when(cityMapper.toSimpleDTOs(cities)).thenReturn(expected);

        Collection<CitySimpleDTO> result = cityService.getCities();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(cityRepository).findAll();
        verify(cityMapper).toSimpleDTOs(cities);
    }
}