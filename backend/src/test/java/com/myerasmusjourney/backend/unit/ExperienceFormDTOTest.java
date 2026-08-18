package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.dto.ExperienceFormDTO;
import com.myerasmusjourney.backend.enumeration.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
public class ExperienceFormDTOTest {

    @Test
    void testExperienceFormDTO() {
        ExperienceFormDTO exp = new ExperienceFormDTO(6.8F, "Titulo1", "Descripción1", Category.Gastronomy.toString(), 2L);

        assertNotNull(exp);
        assertEquals("Titulo1", exp.title());
        assertNotEquals("Description2", exp.description());
        Assertions.assertEquals("Descripción1", exp.description());
        assertEquals(Category.Gastronomy.toString(), exp.category());
        assertEquals(2L, exp.cityId());


        exp = new ExperienceFormDTO(4.7F, "Tittle", "Description", Category.Personal_Experience.toString(), 1L);

        assertEquals(4.7F, exp.rating());
        assertEquals("Tittle", exp.title());
        assertEquals("Description", exp.description());
        assertEquals(Category.Personal_Experience.toString(), exp.category());
        assertEquals(1L, exp.cityId());
    }
}
