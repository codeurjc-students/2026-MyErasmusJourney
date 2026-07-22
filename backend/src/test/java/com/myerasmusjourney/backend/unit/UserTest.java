package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.User;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
public class UserTest {

    @Test
    void testconstructorSetsFieldsAndDefaultRole() {
        User u = new User("John Doe", "jdoe", "john@example.com", "secret");
        assertNull(u.getId());
        assertEquals("John Doe", u.getFullName());
        assertEquals("jdoe", u.getDisplayName());
        assertEquals("john@example.com", u.getEmail());
        assertEquals("secret", u.getEncodedPassword());
        assertNotNull(u.getRoles());
        assertTrue(u.getRoles().contains("USER"));
    }

    @Test
    void testconstructorWithRolesUsesProvidedRoles() {
        var roles = Arrays.asList("ADMIN", "USER");
        User u = new User("Jane", "jane", "jane@example.com", "pw", roles);
        assertEquals(roles, u.getRoles());
    }

    @Test
    void testEmptyConstructor() {
        User u = new User();
        u.setEmail("john@example.com");
        u.setDisplayName("jdoe");
        u.setFullName("John Doe");
        u.setEncodedPassword("secret");
        assertNull(u.getId());
        assertEquals("John Doe", u.getFullName());
        assertEquals("jdoe", u.getDisplayName());
        assertEquals("john@example.com", u.getEmail());
        assertEquals("secret", u.getEncodedPassword());
        assertNotNull(u.getRoles());
        assertTrue(u.getRoles().contains("USER"));
    }
}
