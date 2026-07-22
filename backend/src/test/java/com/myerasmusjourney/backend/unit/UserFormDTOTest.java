package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.dto.UserFormDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserFormDTOTest {

    @Test
    void recordAccessorsReturnValues() {
        UserFormDTO dto = new UserFormDTO(
                "alice@example.com",
                "alice",
                "Alice Example",
                "s3cr3t",
                "s3cr3t"
        );

        assertEquals("alice@example.com", dto.email());
        assertEquals("alice", dto.displayName());
        assertEquals("Alice Example", dto.fullName());
        assertEquals("s3cr3t", dto.password());
        assertEquals("s3cr3t", dto.passwordConfirmation());
    }
}
