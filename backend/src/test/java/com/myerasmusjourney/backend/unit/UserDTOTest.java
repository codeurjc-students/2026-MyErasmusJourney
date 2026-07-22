package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.dto.UserDTO;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
public class UserDTOTest {

    @Test
    void recordAccessorsReturnValues() {
        UserDTO dto = new UserDTO(1L, "John Doe", "jdoe", "john@example.com");
        assertEquals(Long.valueOf(1L), dto.id());
        assertEquals("John Doe", dto.fullName());
        assertEquals("jdoe", dto.displayName());
        assertEquals("john@example.com", dto.email());
    }
}
