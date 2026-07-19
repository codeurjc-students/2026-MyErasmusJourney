package com.myerasmusjourney.backend.integration;

import com.myerasmusjourney.backend.TestDataBase;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.UserFormDTO;
import com.myerasmusjourney.backend.mapper.UserMapper;
import com.myerasmusjourney.backend.repository.UserRepository;
import com.myerasmusjourney.backend.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    List<User> users = List.of(
            new User("TestUser1", "User1", "user1@gmail.com","password"),
            new User("TestUser2", "User2", "user2@gmail.com","password"),
            new User("TestUser3", "User3", "user3@gmail.com","password"),
            new User("TestUser4", "User4", "user4@gmail.com","password")
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
        Long expected = userRepository.findAll().getLast().getId() + 1;

        Long id = userService.createUser(newUser);

        assertNotNull(id);
        Long notExpected = -1L;
        assertNotEquals(notExpected, id);

        assertEquals(expected, id);
    }

    @Test
    void testEmailAlreadyRegistered(){
        UserFormDTO newUser = new UserFormDTO("user1@gmail.com", "Test", "TestUser","password", "password" );
        Long id = userService.createUser(newUser);

        assertNotNull(id);
        Long expected = -1L;
        assertEquals(expected, id);
    }

    @Test
    void testPasswordMismatch(){
        UserFormDTO newUser = new UserFormDTO("user1@gmail.com", "Test", "TestUser","PaSsword", "password" );
        Long id = userService.createUser(newUser);

        assertNull(id);
    }
}
