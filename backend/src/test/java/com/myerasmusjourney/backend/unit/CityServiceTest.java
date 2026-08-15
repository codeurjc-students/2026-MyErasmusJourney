package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.dto.CityDTO;
import com.myerasmusjourney.backend.dto.CityFormDTO;
import com.myerasmusjourney.backend.mapper.CityMapper;
import com.myerasmusjourney.backend.repository.CityRepository;
import com.myerasmusjourney.backend.service.CityService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

        ServletUriComponentsBuilder builder = mock(ServletUriComponentsBuilder.class);

        UriComponents uriComponents = mock(UriComponents.class);

        when(builder.path("/{id}")).thenReturn(builder);
        when(builder.buildAndExpand(savedCity.getId())).thenReturn(uriComponents);
        when(uriComponents.toUri()).thenReturn(URI.create("/cities/1"));

        try (MockedStatic<ServletUriComponentsBuilder> mocked = mockStatic(ServletUriComponentsBuilder.class)) {

            mocked.when(ServletUriComponentsBuilder::fromCurrentRequest).thenReturn(builder);

            ResponseEntity<CityDTO> response = cityService.addCity(cityForm);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(expectedDTO, response.getBody());
            assertEquals(URI.create("/cities/1"), response.getHeaders().getLocation());
        }

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

        ResponseEntity<CityDTO> response = cityService.addCity(cityForm);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDTO, response.getBody());

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

        ServletUriComponentsBuilder builder = mock(ServletUriComponentsBuilder.class);

        UriComponents uriComponents = mock(UriComponents.class);

        when(builder.path("/{id}")).thenReturn(builder);
        when(builder.buildAndExpand(savedCity.getId())).thenReturn(uriComponents);
        when(uriComponents.toUri()).thenReturn(URI.create("/cities/2"));

        try (MockedStatic<ServletUriComponentsBuilder> mocked = mockStatic(ServletUriComponentsBuilder.class)) {

            mocked.when(ServletUriComponentsBuilder::fromCurrentRequest).thenReturn(builder);

            ResponseEntity<CityDTO> response = cityService.addCity(cityForm);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(expectedDTO, response.getBody());
            assertEquals("/cities/2", response.getHeaders().getLocation().getPath());
        }

        verify(cityRepository).findByName("Munich");
        verify(cityRepository).save(any(City.class));
        verify(cityMapper).toDTO(savedCity);
    }
}