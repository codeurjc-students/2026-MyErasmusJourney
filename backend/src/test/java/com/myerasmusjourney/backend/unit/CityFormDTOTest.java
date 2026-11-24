package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.dto.CityFormDTO;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Tag("unit")
public class CityFormDTOTest {

    @Test
    void testCityFormDTO() {
        CityFormDTO city = new CityFormDTO("Madrid", "Capital of Spain", "Spain", 2.52, 1998.3);

        assertEquals("Madrid", city.name());
        assertEquals("Capital of Spain", city.description());
        assertEquals("Spain", city.country());
        assertEquals(2.52, city.longitude());
        assertEquals(1998.3, city.latitude());
    }

    @Test
    void testCityFormDTONullValues() {
        CityFormDTO city = new CityFormDTO(null, null, null, null, null);

        assertNull(city.name());
        assertNull(city.description());
        assertNull(city.country());
        assertNull(city.latitude());
        assertNull(city.longitude());
    }

    @Test
    void testCityFormDTOEquality() {
        CityFormDTO city1 = new CityFormDTO("Madrid", "Capital of Spain", "Spain", 2.52, 1998.3);

        CityFormDTO city2 = new CityFormDTO("Madrid", "Capital of Spain", "Spain", 2.52, 1998.3);

        assertEquals(city1, city2);
        assertEquals(city1.hashCode(), city2.hashCode());
    }
}
