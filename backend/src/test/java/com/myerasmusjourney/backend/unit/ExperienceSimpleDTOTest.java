package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.dto.ExperienceSimpleDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ExperienceSimpleDTOTest {

    @Test
    void testDTOCreation() {
        ExperienceSimpleDTO exp = new ExperienceSimpleDTO(3L, LocalDate.of(2024, 3, 26), 6.8F, "Titulo1", "Descripción1");
        assertNotNull(exp);
        assertEquals(3L, exp.id());
        assertEquals(LocalDate.of(2024, 3, 26), exp.date());
        assertEquals(6.8F, exp.rating());
        assertEquals("Titulo1", exp.title());
        assertNotEquals("Description2", exp.description());
        assertEquals("Descripción1", exp.description());

        exp = new ExperienceSimpleDTO(null, LocalDate.of(2026, 1, 15), 4.7F, "Tittle", "Description");

        assertNull(exp.id());
        assertEquals(LocalDate.of(2026, 1, 15), exp.date());
        assertEquals(4.7F, exp.rating());
        assertEquals("Tittle", exp.title());
        assertEquals("Description", exp.description());
    }

    @Test
    void testComparingDTOs() {
        ExperienceSimpleDTO exp = new ExperienceSimpleDTO(3L, LocalDate.of(2024, 3, 26), 6.8F, "Titulo1", "Descripción1");
        ExperienceSimpleDTO exp2 = new ExperienceSimpleDTO(3L, LocalDate.of(2024, 3, 26), 6.8F, "Titulo1", "Descripción1");
        ExperienceSimpleDTO exp3 = new ExperienceSimpleDTO(4L, LocalDate.of(2024, 3, 26), 6.8F, "Titulo1", "Descripción1");

        assertEquals(exp, exp2);
        assertNotEquals(exp, exp3);
    }


}
