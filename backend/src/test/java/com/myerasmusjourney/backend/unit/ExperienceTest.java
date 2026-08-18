package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.enumeration.Category;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Tag("unit")
public class ExperienceTest {

    @Test
    void testContrsuctor(){
        Experience experience = new Experience("Titulo","Descripcion",3.4F, Category.Personal_Experience.toString(), null, null);

        assertNull(experience.getId());
        assertEquals(LocalDate.now(), experience.getDate());
        assertEquals("Titulo", experience.getTitle());
        assertEquals("Descripcion", experience.getDescription());
        assertEquals(3.4F, experience.getRating());
        assertNull(experience.getAuthor());
        assertNull(experience.getCity());
        assertEquals(Category.Personal_Experience, experience.getCategory());
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
