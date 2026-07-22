package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.UserDTO;
import com.myerasmusjourney.backend.mapper.UserMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
public class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void testToDTO() {
        User u = new User("John Doe", "jdoe", "john@example.com", "secret");

        UserDTO expected = new UserDTO(null, "John Doe", "jdoe", "john@example.com");

        UserDTO result = mapper.toDTO(u);

        assertEquals(expected, result);
    }

    @Test
    void nullSourceReturnsNull() {
        assertNull(mapper.toDTO(null));
    }
}
