package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.UserFormDTO;
import com.myerasmusjourney.backend.mapper.UserMapper;
import com.myerasmusjourney.backend.repository.UserRepository;
import com.myerasmusjourney.backend.service.UserService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void testSuccessfulCreateUser(){
        UserFormDTO newUser = new UserFormDTO("test@gmail.com", "Test", "TestUser","password", "password" );
        when(userRepository.findByEmail(newUser.email())).thenReturn(null);

        User savedUser = new User("Test", "TestUser", "test@gmail.com", "password");
        savedUser.setId(1L);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        Long userId = userService.createUser(newUser);
        Long expected = 1L;
        assertEquals(expected, userId);

        verify(userRepository).findByEmail(newUser.email());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testEmailAlreadyRegistered(){
        UserFormDTO newUser = new UserFormDTO("test@gmail.com", "Test", "TestUser","password", "password" );
        User user = new User("Test", "TestUser", "test@gmail.com", "password");
        when(userRepository.findByEmail(newUser.email())).thenReturn(user);

        Long userId = userService.createUser(newUser);
        Long expected = -1L;
        assertEquals(expected, userId);

        verify(userRepository).findByEmail(newUser.email());
    }

    @Test
    void testPasswordMismatch(){
        UserFormDTO newUser = new UserFormDTO("test@gmail.com", "Test", "TestUser","password", "pAssword" );

        Long userId = userService.createUser(newUser);
        assertNull(userId);
    }

}
