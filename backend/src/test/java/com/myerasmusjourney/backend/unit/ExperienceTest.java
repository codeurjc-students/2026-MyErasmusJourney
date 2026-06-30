package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.Experience;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Tag("unit")
public class ExperienceTest {

    @Test
    void testContrsuctor(){
        Experience experience = new Experience("Titulo","Descripcion",3.4F);

        assertNull(experience.getId());
        assertEquals(LocalDate.now(), experience.getDate());
        assertEquals("Titulo", experience.getTitle());
        assertEquals("Descripcion", experience.getDescription());
        assertEquals(3.4F, experience.getRating());
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
