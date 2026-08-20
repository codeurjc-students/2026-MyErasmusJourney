package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.dto.ExperienceSimpleDTO;
import com.myerasmusjourney.backend.enumeration.Category;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
public class ExperienceSimpleDTOTest {

    @Test
    void testDTOCreation() {
        ExperienceSimpleDTO exp = new ExperienceSimpleDTO(3L, LocalDate.of(2024, 3, 26), 6.8F, "Titulo1", "Descripción1", List.of(Category.Accommodation, Category.Transportation));
        assertNotNull(exp);
        assertEquals(3L, exp.id());
        assertEquals(LocalDate.of(2024, 3, 26), exp.date());
        assertEquals(6.8F, exp.rating());
        assertEquals("Titulo1", exp.title());
        assertNotEquals("Description2", exp.description());
        assertEquals("Descripción1", exp.description());
        assertEquals(2, exp.categories().size());

        exp = new ExperienceSimpleDTO(null, LocalDate.of(2026, 1, 15), 4.7F, "Tittle", "Description", List.of(Category.Gastronomy, Category.Culture, Category.Social_Events));

        assertNull(exp.id());
        assertEquals(LocalDate.of(2026, 1, 15), exp.date());
        assertEquals(4.7F, exp.rating());
        assertEquals("Tittle", exp.title());
        assertEquals("Description", exp.description());
        assertTrue(exp.categories().contains(Category.Culture));
        assertTrue(exp.categories().contains(Category.Gastronomy));
        assertTrue(exp.categories().contains(Category.Social_Events));

    }

    @Test
    void testComparingDTOs() {
        ExperienceSimpleDTO exp = new ExperienceSimpleDTO(3L, LocalDate.of(2024, 3, 26), 6.8F, "Titulo1", "Descripción1", List.of(Category.Personal_Experience));
        ExperienceSimpleDTO exp2 = new ExperienceSimpleDTO(3L, LocalDate.of(2024, 3, 26), 6.8F, "Titulo1", "Descripción1", List.of(Category.Personal_Experience));
        ExperienceSimpleDTO exp3 = new ExperienceSimpleDTO(4L, LocalDate.of(2024, 3, 26), 6.8F, "Titulo1", "Descripción1", List.of(Category.Personal_Experience, Category.Culture));

        assertEquals(exp, exp2);
        assertNotEquals(exp, exp3);
    }


}
