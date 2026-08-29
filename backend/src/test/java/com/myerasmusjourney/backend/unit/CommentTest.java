package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.Comment;
import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.User;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Tag("unit")
public class CommentTest {

    @Test
    void testEmptyConstructor(){
        Comment comment = new Comment();
        assertNull(comment.getId());
        assertNull(comment.getAuthor());
        assertNull(comment.getExperience());
        assertNull(comment.getDate());
    }

    @Test
    void testConstructor(){
        Experience experience = new Experience();
        User user = new User();
        Comment comment = new Comment("test", user, experience);
        assertNull(comment.getId());
        assertEquals(LocalDate.now(), comment.getDate());
        assertEquals("test", comment.getDescription());
        assertEquals(experience, comment.getExperience());
        assertEquals(user, comment.getAuthor());
    }

    @Test
    void testSetters(){
        Long id = 1L;
        Experience experience = new Experience();
        User user = new User();
        Comment comment = new Comment();
        assertNull(comment.getId());
        assertNull(comment.getAuthor());
        assertNull(comment.getExperience());
        assertNull(comment.getDate());

        comment.setId(id);
        comment.setAuthor(user);
        comment.setExperience(experience);
        comment.setDescription("test");
        comment.setDate(LocalDate.now());

        assertEquals(id, comment.getId());
        assertEquals(LocalDate.now(), comment.getDate());
        assertEquals("test", comment.getDescription());
        assertEquals(experience, comment.getExperience());
        assertEquals(user, comment.getAuthor());
    }
}
