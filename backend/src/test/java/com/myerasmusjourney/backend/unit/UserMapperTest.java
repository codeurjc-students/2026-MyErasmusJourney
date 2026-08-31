package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.Comment;
import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.CommentSimpleDTO;
import com.myerasmusjourney.backend.dto.ExperienceSimpleDTO;
import com.myerasmusjourney.backend.dto.UserDTO;
import com.myerasmusjourney.backend.dto.UserSimpleDTO;
import com.myerasmusjourney.backend.mapper.UserMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
public class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void testToSimpleDTO() {
        User u = new User("John Doe", "jdoe", "john@example.com", "secret", "Munich", "Germany");

        UserSimpleDTO expected = new UserSimpleDTO(null, "jdoe", "john@example.com");

        UserSimpleDTO result = mapper.toSimpleDTO(u);

        assertEquals(expected, result);
    }

    @Test
    void nullSourceReturnsNull() {
        assertNull(mapper.toSimpleDTO(null));
    }

    @Test
    void testToDTO() {
        User u = new User("John Doe", "jdoe", "john@example.com", "secret", "Munich", "Germany");

        u.addExperience(new Experience());

        u.addComment(new Comment());

        UserDTO expected = new UserDTO(null, "John Doe", "jdoe", "john@example.com", "Munich, Germany", List.of("USER"), List.of(new ExperienceSimpleDTO(null, null, null, null, null, List.of(), null, null, null)), List.of(new CommentSimpleDTO(null, null, null, null)));

        UserDTO result = mapper.toDTO(u);

        assertEquals(expected.id(), result.id());
        assertEquals(expected.fullName(), result.fullName());
        assertEquals(expected.displayName(), result.displayName());
        assertEquals(expected.email(), result.email());
        assertEquals(expected.studyLocation(), result.studyLocation());
        assertEquals(expected.roles(), result.roles());
        assertEquals(expected.experiences(), result.experiences());
        assertEquals(expected.comments(), result.comments());
    }
}
