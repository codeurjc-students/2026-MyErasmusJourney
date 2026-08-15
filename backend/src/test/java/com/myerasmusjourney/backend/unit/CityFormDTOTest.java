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
        CityFormDTO city = new CityFormDTO("Madrid", "Capital of Spain", "Spain");

        assertEquals("Madrid", city.name());
        assertEquals("Capital of Spain", city.description());
        assertEquals("Spain", city.country());
    }

    @Test
    void testCityFormDTONullValues() {
        CityFormDTO city = new CityFormDTO(null, null, null);

        assertNull(city.name());
        assertNull(city.description());
        assertNull(city.country());
    }

    @Test
    void testCityFormDTOEquality() {
        CityFormDTO city1 = new CityFormDTO("Madrid", "Capital of Spain", "Spain");

        CityFormDTO city2 = new CityFormDTO("Madrid", "Capital of Spain", "Spain");

        assertEquals(city1, city2);
        assertEquals(city1.hashCode(), city2.hashCode());
    }
}
