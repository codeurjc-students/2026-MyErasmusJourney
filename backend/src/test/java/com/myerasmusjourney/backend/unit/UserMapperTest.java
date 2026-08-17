package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.UserDTO;
import com.myerasmusjourney.backend.dto.UserSimpleDTO;
import com.myerasmusjourney.backend.mapper.UserMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

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

        UserDTO expected = new UserDTO(null, "John Doe", "jdoe", "john@example.com", "Munich, Germany", List.of("USER"));

        UserDTO result = mapper.toDTO(u);

        assertEquals(expected, result);
    }
}
