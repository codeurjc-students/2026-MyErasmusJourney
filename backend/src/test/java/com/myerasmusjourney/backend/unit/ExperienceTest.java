package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.enumeration.Category;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Tag("unit")
public class ExperienceTest {

    @Test
    void testConstructor(){
        Experience experience = new Experience("Titulo","Descripcion",3.4F, List.of("Personal_Experience"), null, null);

        assertNull(experience.getId());
        assertEquals(LocalDate.now(), experience.getDate());
        assertEquals("Titulo", experience.getTitle());
        assertEquals("Descripcion", experience.getDescription());
        assertEquals(3.4F, experience.getRating());
        assertEquals(LocalDate.now(), experience.getDate());
        assertNull(experience.getAuthor());
        assertNull(experience.getCity());
        assertTrue(experience.getCategories().contains(Category.Personal_Experience));
    }

    @Test
    void testEmptyConstructor(){
        Experience experience =new Experience();

        assertNull(experience.getId());
        assertNull(experience.getDate());
        assertNull(experience.getTitle());
        assertNull(experience.getDescription());
        assertNull(experience.getRating());

    }
}
