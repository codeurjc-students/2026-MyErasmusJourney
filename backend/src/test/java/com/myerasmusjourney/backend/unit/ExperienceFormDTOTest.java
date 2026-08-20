package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.dto.ExperienceFormDTO;
import com.myerasmusjourney.backend.enumeration.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
public class ExperienceFormDTOTest {

    @Test
    void testExperienceFormDTO() {
        ExperienceFormDTO exp = new ExperienceFormDTO(6.8F, "Titulo1", "Descripción1", null, List.of("Gastronomy", "Documentation"), 2L);

        assertNotNull(exp);
        assertEquals("Titulo1", exp.title());
        assertNotEquals("Description2", exp.description());
        Assertions.assertEquals("Descripción1", exp.description());
        assertEquals(Category.Gastronomy.toString(), exp.categories().getFirst());
        assertEquals(2L, exp.cityId());
        assertNull(exp.date());


        exp = new ExperienceFormDTO(4.7F, "Tittle", "Description", LocalDate.now(), List.of("Personal_Experience", "Culture"), 1L);

        assertEquals(4.7F, exp.rating());
        assertEquals("Tittle", exp.title());
        assertEquals("Description", exp.description());
        assertEquals(2, exp.categories().size());
        assertEquals(1L, exp.cityId());
        assertEquals(LocalDate.now(), exp.date());
    }
}
