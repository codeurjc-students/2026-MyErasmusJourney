package com.myerasmusjourney.backend.integration;

import com.myerasmusjourney.backend.TestDataBase;
import com.myerasmusjourney.backend.dto.LocationDTO;
import com.myerasmusjourney.backend.service.MapService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
public class MapServiceTest extends TestDataBase {

    @Autowired
    private MapService mapService;

    @Test
    void shouldReturnLocationWhenCityIsFound() {
        LocationDTO result = mapService.geoLocateCity("Madrid", "Spain");

        assertNotNull(result);
        assertEquals(40.416782, result.latitude(), 0.1);
        assertEquals(-3.703507, result.longitude(), 0.1);
    }

    @Test
    void shouldReturnNullWhenCityIsNotFound() {
        LocationDTO result = mapService.geoLocateCity(
                "ThisCityDoesNotExist123456",
                "ThisCountryDoesNotExist123456"
        );
        assertNull(result);
    }
}
