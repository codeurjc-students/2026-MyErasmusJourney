package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.dto.CityDTO;
import com.myerasmusjourney.backend.dto.ExperienceSimpleDTO;
import com.myerasmusjourney.backend.enumeration.Category;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Tag("unit")
public class CityDTOTest {
    @Test
    void testCityDTO() {
        ExperienceSimpleDTO experience = new ExperienceSimpleDTO(1L, LocalDate.now(), 5F, "Ttitle", "Description", List.of(Category.Accommodation), null, null, null);

        List<ExperienceSimpleDTO> experiences = List.of(experience);

        CityDTO city = new CityDTO(1L, "Madrid", "Capital of Spain", "Spain", 1.2, experiences);

        assertEquals(1L, city.id());
        assertEquals("Madrid", city.name());
        assertEquals("Capital of Spain", city.description());
        assertEquals("Spain", city.country());
        assertEquals(1.2, city.averageRating());
        assertEquals(experiences, city.experiences());
    }

    @Test
    void testCityDTONullValues() {
        CityDTO city = new CityDTO(null, null, null, null,  null, null);

        assertNull(city.id());
        assertNull(city.name());
        assertNull(city.description());
        assertNull(city.country());
        assertNull(city.averageRating());
        assertNull(city.experiences());
    }

    @Test
    void testCityDTOEquality() {
        List<ExperienceSimpleDTO> experiences = List.of();

        CityDTO city1 = new CityDTO(1L, "Madrid", "Capital of Spain", "Spain", 3.4, experiences);

        CityDTO city2 = new CityDTO(1L, "Madrid", "Capital of Spain", "Spain", 3.4, experiences);

        assertEquals(city1, city2);
        assertEquals(city1.hashCode(), city2.hashCode());
    }
}
