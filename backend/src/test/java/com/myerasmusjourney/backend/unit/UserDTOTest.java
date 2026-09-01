package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.dto.ExperienceSimpleDTO;
import com.myerasmusjourney.backend.dto.UserDTO;
import com.myerasmusjourney.backend.enumeration.Category;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Tag("unit")
public class UserDTOTest {

    @Test
    void testUserDTO() {
        ExperienceSimpleDTO experienceSimpleDTO = new ExperienceSimpleDTO(1L, LocalDate.now(), 9.3F, "title", "description", List.of(Category.Personal_Experience, Category.Culture), "Berlin", "Germany", "testDTO");
        ExperienceSimpleDTO experienceSimpleDTO2 = new ExperienceSimpleDTO(2L, LocalDate.now(), 9.3F, "title", "description", List.of(Category.Personal_Experience, Category.Culture), "Berlin", "Germany", "testDTO");

        UserDTO dto = new UserDTO(1L, "John Doe", "jdoe", "john@example.com", "Munich, Germany", List.of("USER", "ADMIN"), List.of(experienceSimpleDTO, experienceSimpleDTO2), List.of());
        assertEquals(Long.valueOf(1L), dto.id());
        assertEquals("John Doe", dto.fullName());
        assertEquals("jdoe", dto.displayName());
        assertEquals("john@example.com", dto.email());
        assertEquals("Munich, Germany", dto.studyLocation());
        assertEquals(List.of("USER", "ADMIN"), dto.roles());
        assertEquals(List.of(experienceSimpleDTO, experienceSimpleDTO2), dto.experiences());
        assertTrue(dto.comments().isEmpty());
    }
}
