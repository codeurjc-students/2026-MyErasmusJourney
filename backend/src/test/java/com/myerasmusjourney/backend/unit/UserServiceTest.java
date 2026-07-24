package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.UserFormDTO;
import com.myerasmusjourney.backend.dto.UserSimpleDTO;
import com.myerasmusjourney.backend.mapper.UserMapper;
import com.myerasmusjourney.backend.repository.UserRepository;
import com.myerasmusjourney.backend.service.UserService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void testSuccessfulCreateUser(){
        UserFormDTO newUser = new UserFormDTO("test@gmail.com", "Test", "TestUser","password", "password" );
        when(userRepository.findByEmail(newUser.email())).thenReturn(null);

        User savedUser = new User("Test", "TestUser", "test@gmail.com", "password");
        savedUser.setId(1L);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserSimpleDTO DTO = new UserSimpleDTO(1L, "TestUser", "Test", "password");
        when(userMapper.toSimpleDTO(any(User.class))).thenReturn(DTO);

        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

        UserSimpleDTO userDTO = userService.createUser(newUser);
        Long expectedId = 1L;
        assertEquals(expectedId, userDTO.id());
        assertEquals(DTO, userDTO);

        verify(userRepository).findByEmail(newUser.email());
        verify(userMapper).toSimpleDTO(any(User.class));
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testEmailAlreadyRegistered(){
        UserFormDTO newUser = new UserFormDTO("test@gmail.com", "Test", "TestUser","password", "password" );
        User user = new User("Test", "TestUser", "test@gmail.com", "password");
        when(userRepository.findByEmail(newUser.email())).thenReturn(user);

        UserSimpleDTO DTO = new UserSimpleDTO(null, "TestUser", "Test", "password");
        when(userMapper.toSimpleDTO(any(User.class))).thenReturn(DTO);

        UserSimpleDTO userDTO = userService.createUser(newUser);
        assertNull(userDTO.id());

        verify(userRepository).findByEmail(newUser.email());
        verify(userMapper).toSimpleDTO(any(User.class));

    }

    @Test
    void testPasswordMismatch(){
        UserFormDTO newUser = new UserFormDTO("test@gmail.com", "Test", "TestUser","password", "pAssword" );

        UserSimpleDTO userDTO = userService.createUser(newUser);
        assertNull(userDTO);
    }

}
