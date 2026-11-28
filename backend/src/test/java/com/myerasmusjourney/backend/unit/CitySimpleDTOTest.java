package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.dto.CitySimpleDTO;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Tag("unit")
public class CitySimpleDTOTest {

    @Test
    void testCityDTO() {

        CitySimpleDTO city = new CitySimpleDTO(1L, "Madrid", "Capital of Spain", "Spain", 1.2, -1.4, 192.1);

        assertEquals(1L, city.id());
        assertEquals("Madrid", city.name());
        assertEquals("Capital of Spain", city.description());
        assertEquals("Spain", city.country());
        assertEquals(1.2, city.averageRating());
        assertEquals(-1.4, city.latitude());
        assertEquals(192.1, city.longitude());
    }

    @Test
    void testCityDTONullValues() {
        CitySimpleDTO city = new CitySimpleDTO(null, null, null, null, null, null, null);

        assertNull(city.id());
        assertNull(city.name());
        assertNull(city.description());
        assertNull(city.country());
    }

    @Test
    void testCityDTOEquality() {

        CitySimpleDTO city1 = new CitySimpleDTO(1L, "Madrid", "Capital of Spain", "Spain", 1.2, -1.4, 192.1);

        CitySimpleDTO city2 = new CitySimpleDTO(1L, "Madrid", "Capital of Spain", "Spain", 1.2, -1.4, 192.1);

        assertEquals(city1, city2);
        assertEquals(city1.hashCode(), city2.hashCode());
    }
}
