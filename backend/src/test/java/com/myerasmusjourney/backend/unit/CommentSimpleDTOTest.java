package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.dto.CommentSimpleDTO;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("unit")
public class CommentSimpleDTOTest {

    @Test
    void testCommentSimpleDTO(){
        CommentSimpleDTO commentSimpleDTO = new CommentSimpleDTO(1L, LocalDate.now(), "comment simple dto", "testing");
        assertEquals(1, commentSimpleDTO.id().intValue());
        assertEquals(LocalDate.now(), commentSimpleDTO.date());
        assertEquals("comment simple dto", commentSimpleDTO.description());
        assertEquals("testing", commentSimpleDTO.authorName());
    }
}
