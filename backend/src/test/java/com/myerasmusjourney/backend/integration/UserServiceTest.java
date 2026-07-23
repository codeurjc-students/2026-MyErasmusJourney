package com.myerasmusjourney.backend.integration;

import com.myerasmusjourney.backend.TestDataBase;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.UserDTO;
import com.myerasmusjourney.backend.dto.UserFormDTO;
import com.myerasmusjourney.backend.mapper.UserMapper;
import com.myerasmusjourney.backend.repository.UserRepository;
import com.myerasmusjourney.backend.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@Tag("integration")
public class UserServiceTest extends TestDataBase{

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    List<User> users = List.of(
            new User("TestUser1", "User1", "user1@gmail.com",passwordEncoder.encode("password")),
            new User("TestUser2", "User2", "user2@gmail.com",passwordEncoder.encode("password")),
            new User("TestUser3", "User3", "user3@gmail.com",passwordEncoder.encode("password")),
            new User("TestUser4", "User4", "user4@gmail.com",passwordEncoder.encode("password"))
    );

    @BeforeEach
    void saveUsers(){
        userRepository.saveAll(users);
    }

    @AfterEach
    void deleteUser(){
        userRepository.deleteAll();
    }

    @Test
    void testSuccessfulCreateUser(){
        UserFormDTO newUser = new UserFormDTO("test@gmail.com", "Test", "TestUser","password", "password" );

        Long expectedId = userRepository.findAll().getLast().getId() + 1;
        User expectedUser = new User("TestUser", "Test", "test@gmail.com", passwordEncoder.encode("password"));
        expectedUser.setId(expectedId);

        UserDTO userDTO = userService.createUser(newUser);

        assertNotNull(userDTO);
        User notExpectedUser = new User("Test", "TestUser", "test@gmail.com", passwordEncoder.encode("password"));
        notExpectedUser.setId(-1L);
        UserDTO notExpected = userMapper.toDTO(notExpectedUser);
        assertNotEquals(notExpected, userDTO);


        UserDTO expected = userMapper.toDTO(expectedUser);
        assertEquals(expected, userDTO);
    }

    @Test
    void testEmailAlreadyRegistered(){
        UserFormDTO newUser = new UserFormDTO("user1@gmail.com", "Test", "TestUser","password", "password" );

        userService.createUser(newUser);

        UserDTO userDTO = userService.createUser(newUser);

        assertNull(userDTO.id());
    }

    @Test
    void testPasswordMismatch(){
        UserFormDTO newUser = new UserFormDTO("user1@gmail.com", "Test", "TestUser","PaSsword", "password" );
        UserDTO userDTO = userService.createUser(newUser);

        assertNull(userDTO);
    }
}
