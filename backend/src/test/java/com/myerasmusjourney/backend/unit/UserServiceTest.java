package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.UserDTO;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        UserFormDTO newUser = new UserFormDTO("test@gmail.com", "Test", "TestUser","Munich", "Germany", "password", "password" );
        when(userRepository.findByEmail(newUser.email())).thenReturn(null);

        User savedUser = new User("Test", "TestUser", "test@gmail.com", "password", "Munich", "Germany");
        savedUser.setId(1L);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserSimpleDTO DTO = new UserSimpleDTO(1L, "Test", "password");
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
        UserFormDTO newUser = new UserFormDTO("test@gmail.com", "Test", "TestUser","Munich", "Germany", "password", "password" );
        User user = new User("Test", "TestUser", "test@gmail.com", "password", "Munich", "Germany");
        when(userRepository.findByEmail(newUser.email())).thenReturn(user);

        UserSimpleDTO DTO = new UserSimpleDTO(null, "Test", "password");
        when(userMapper.toSimpleDTO(any(User.class))).thenReturn(DTO);

        UserSimpleDTO userDTO = userService.createUser(newUser);
        assertNull(userDTO.id());

        verify(userRepository).findByEmail(newUser.email());
        verify(userMapper).toSimpleDTO(any(User.class));

    }

    @Test
    void testPasswordMismatch(){
        UserFormDTO newUser = new UserFormDTO("test@gmail.com", "Test", "TestUser","Germany", "Munich", "password", "pAssword" );

        UserSimpleDTO userDTO = userService.createUser(newUser);
        assertNull(userDTO);
    }

    @Test
    void testGetUserInfo(){
        UserSimpleDTO userSimpleDTO = new UserSimpleDTO(1L, "test", "test@email.com");
        User user = new User("test", "testUser", "test@email.com", "password", "Munich", "Germany");

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@email.com");
        when (userRepository.findByEmail(any(String.class))).thenReturn(user);
        when(userMapper.toSimpleDTO(any(User.class))).thenReturn(userSimpleDTO);

        UserSimpleDTO result = userService.getUserInfo();

        assertNotNull(result);
        assertEquals(userSimpleDTO, result);

        verify(securityContext).getAuthentication();
        verify(authentication).getName();
        verify(userRepository).findByEmail(any(String.class));
        verify(userMapper).toSimpleDTO(any(User.class));
    }

    @Test
    void testGetUnregisteredUserInfo(){
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("");
        when (userRepository.findByEmail(any(String.class))). thenReturn(null);
        when(userMapper.toSimpleDTO(null)).thenReturn(null);

        UserSimpleDTO result = userService.getUserInfo();

        assertNull(result);

        verify(securityContext).getAuthentication();
        verify(authentication).getName();
        verify(userRepository).findByEmail(any(String.class));
        verify(userMapper).toSimpleDTO(null);
    }

    @Test
    void testNullAuthentication(){
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(null);
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(null);
        when(userMapper.toSimpleDTO(null)).thenReturn(null);

        UserSimpleDTO result = userService.getUserInfo();

        assertNull(result);

        verify(securityContext).getAuthentication();
        verify(userMapper).toSimpleDTO(null);
    }

    @Test
    void testGetUserByIdNotAuthenticated() {
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(null);

        UserDTO result = userService.getUserById(1L);

        assertNull(result);

        verify(securityContext).getAuthentication();
        verifyNoInteractions(userRepository);
        verifyNoInteractions(userMapper);
    }

    @Test
    void testGetUserByIdForbidden() {
        User loggedUser = new User("Test", "user", "test@email.com", "password", "Munich", "Germany");
        loggedUser.setId(1L);

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@email.com");

        when(userRepository.findByEmail("test@email.com"))
                .thenReturn(loggedUser);

        UserDTO result = userService.getUserById(2L);

        assertNull(result);

        verify(userRepository).findByEmail("test@email.com");
        verify(userRepository, never()).findById(anyLong());
        verifyNoInteractions(userMapper);
    }

    @Test
    void testGetUserByIdSuccess() {
        User loggedUser = new User("Test", "user", "test@email.com", "password", "Munich", "Germany");
        loggedUser.setId(1L);

        User targetUser = new User("John", "john", "john@email.com", "password", "Munich", "Germany");
        targetUser.setId(1L);

        UserDTO dto = new UserDTO(
                1L,
                "John",
                "john",
                "john@email.com",
                "Munich, Germany",
                List.of("USER")
        );

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@email.com");

        when(userRepository.findByEmail("test@email.com"))
                .thenReturn(loggedUser);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(targetUser));

        when(userMapper.toDTO(targetUser))
                .thenReturn(dto);

        UserDTO result = userService.getUserById(1L);

        assertEquals(dto, result);

        verify(userRepository).findByEmail("test@email.com");
        verify(userRepository).findById(1L);
        verify(userMapper).toDTO(targetUser);
    }

    @Test
    void testGetUserByIdSuccessNoStudyLocation() {
        User loggedUser = new User("Test", "user", "test@email.com", "password", "Munich", "Germany");
        loggedUser.setId(1L);

        User targetUser = new User("John", "john", "john@email.com", "password", null, "Germany");
        targetUser.setId(1L);

        UserDTO dto = new UserDTO(
                1L,
                "John",
                "john",
                "john@email.com",
                null,
                List.of("USER")
        );

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@email.com");

        when(userRepository.findByEmail("test@email.com"))
                .thenReturn(loggedUser);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(targetUser));

        when(userMapper.toDTO(targetUser))
                .thenReturn(dto);

        UserDTO result = userService.getUserById(1L);

        assertEquals(dto, result);

        verify(userRepository).findByEmail("test@email.com");
        verify(userRepository).findById(1L);
        verify(userMapper).toDTO(targetUser);
    }

    @Test
    void testGetUserByIdNotFound() {
        User loggedUser = new User("Test", "user", "test@email.com", "password", "Munich", "Germany");
        loggedUser.setId(1L);

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@email.com");

        when(userRepository.findByEmail("test@email.com"))
                .thenReturn(loggedUser);

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> userService.getUserById(1L)
        );

        verify(userRepository).findById(1L);
        verifyNoInteractions(userMapper);
    }

    @Test
    void testGetUserByIdAdmin() {
        User admin = new User("Admin", "admin", "admin@email.com", "password", "Munich", "Germany");
        admin.setId(99L);
        admin.getRoles().add("ADMIN");

        User targetUser = new User("John", "john", "john@email.com", "password", "Munich", "Germany");
        targetUser.setId(1L);

        UserDTO dto = new UserDTO(
                1L,
                "John",
                "john",
                "john@email.com",
                "Munich, Germany",
                List.of("USER")
        );

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("admin@email.com");

        when(userRepository.findByEmail("admin@email.com"))
                .thenReturn(admin);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(targetUser));

        when(userMapper.toDTO(targetUser))
                .thenReturn(dto);

        UserDTO result = userService.getUserById(1L);

        assertEquals(dto, result);

        verify(userRepository).findByEmail("admin@email.com");
        verify(userRepository).findById(1L);
        verify(userMapper).toDTO(targetUser);
    }

}
