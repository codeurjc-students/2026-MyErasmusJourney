package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.dto.CitySimpleDTO;
import com.myerasmusjourney.backend.dto.ExperienceDTO;
import com.myerasmusjourney.backend.dto.UserSimpleDTO;
import com.myerasmusjourney.backend.enumeration.Category;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Tag("unit")
public class ExperienceDTOTest {

    @Test
    void testDTOCreation() {
        CitySimpleDTO citySimpleDTO = new CitySimpleDTO(1L, "Madrid", "Description", "Spain");
        ExperienceDTO exp = new ExperienceDTO(3L, LocalDate.of(2024, 3, 26), 6.8F, "Titulo1", "Descripción1", List.of(Category.Culture, Category.Transportation), citySimpleDTO, null);
        assertNotNull(exp);
        assertEquals(3L, exp.id());
        assertEquals(LocalDate.of(2024, 3, 26), exp.date());
        assertEquals(6.8F, exp.rating());
        assertEquals("Titulo1", exp.title());
        assertNotEquals("Description2", exp.description());
        assertEquals("Descripción1", exp.description());
        assertEquals(List.of(Category.Culture, Category.Transportation), exp.categories());
        assertNull(exp.author());
        assertEquals(citySimpleDTO, exp.city());

        UserSimpleDTO userSimpleDTO = new UserSimpleDTO(2L, "Test", "test@gamil.com");

        exp = new ExperienceDTO(null, LocalDate.of(2026, 1, 15), 4.7F, "Tittle", "Description", List.of(Category.Accommodation, Category.Transportation), null, userSimpleDTO);

        assertNull(exp.id());
        assertEquals(LocalDate.of(2026, 1, 15), exp.date());
        assertEquals(4.7F, exp.rating());
        assertEquals("Tittle", exp.title());
        assertEquals("Description", exp.description());
        assertEquals(userSimpleDTO, exp.author());
        assertNull(exp.city());
    }

    @Test
    void testComparingDTOs() {
        UserSimpleDTO userSimpleDTO = new UserSimpleDTO(2L, "Test", "test@gamil.com");
        CitySimpleDTO citySimpleDTO = new CitySimpleDTO(1L, "Madrid", "Description", "Spain");

        ExperienceDTO exp = new ExperienceDTO(3L, LocalDate.of(2024, 3, 26), 6.8F, "Titulo1", "Descripción1", List.of(Category.Culture, Category.Transportation), citySimpleDTO, userSimpleDTO);
        ExperienceDTO exp2 = new ExperienceDTO(3L, LocalDate.of(2024, 3, 26), 6.8F, "Titulo1", "Descripción1", List.of(Category.Culture, Category.Transportation), citySimpleDTO, userSimpleDTO);
        ExperienceDTO exp3 = new ExperienceDTO(4L, LocalDate.of(2024, 3, 26), 6.8F, "Titulo1", "Descripción1", List.of(Category.Culture, Category.Transportation), citySimpleDTO, userSimpleDTO);

        assertEquals(exp, exp2);
        assertNotEquals(exp, exp3);
    }
}
