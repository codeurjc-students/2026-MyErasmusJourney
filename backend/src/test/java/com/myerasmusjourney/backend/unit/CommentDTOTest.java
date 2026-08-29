package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.dto.CommentDTO;
import com.myerasmusjourney.backend.dto.ExperienceSimpleDTO;
import com.myerasmusjourney.backend.dto.UserSimpleDTO;
import com.myerasmusjourney.backend.enumeration.Category;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Tag("unit")
public class CommentDTOTest {

    @Test
    void testCommentDTO(){
        UserSimpleDTO userSimpleDTO = new UserSimpleDTO(2L, "test", "test@email.com");
        ExperienceSimpleDTO experienceSimpleDTO = new ExperienceSimpleDTO(3L, LocalDate.now(), 1.3F, "title", "description", List.of(Category.Personal_Experience), "Athens", "Greece", "test");
        CommentDTO commentDTO = new CommentDTO(1L, LocalDate.now(), "test", userSimpleDTO, experienceSimpleDTO);

        assertEquals(1, commentDTO.id().intValue());
        assertEquals("test", commentDTO.description());
        assertEquals(LocalDate.now(), commentDTO.date());
        assertEquals(experienceSimpleDTO, commentDTO.experience());
        assertEquals(userSimpleDTO, commentDTO.author());
    }
}
