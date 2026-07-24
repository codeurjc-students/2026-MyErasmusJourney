package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.dto.UserSimpleDTO;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

@Tag("unit")
public class UserSimpleDTOTest {

    @Test
    void recordAccessorsReturnValues() {
        UserSimpleDTO dto = new UserSimpleDTO(1L, "John Doe", "jdoe", "john@example.com");
        assertEquals(Long.valueOf(1L), dto.id());
        assertEquals("John Doe", dto.fullName());
        assertEquals("jdoe", dto.displayName());
        assertEquals("john@example.com", dto.email());
    }
}
