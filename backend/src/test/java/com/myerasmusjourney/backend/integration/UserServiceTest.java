package com.myerasmusjourney.backend.integration;

import com.myerasmusjourney.backend.TestDataBase;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.UserDTO;
import com.myerasmusjourney.backend.dto.UserFormDTO;
import com.myerasmusjourney.backend.dto.UserSimpleDTO;
import com.myerasmusjourney.backend.mapper.UserMapper;
import com.myerasmusjourney.backend.repository.UserRepository;
import com.myerasmusjourney.backend.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.NoSuchElementException;

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
            new User("TestUser1", "User1", "user1@gmail.com",passwordEncoder.encode("password"), "Munich", "Germany"),
            new User("TestUser2", "User2", "user2@gmail.com",passwordEncoder.encode("password"), "Munich", "Germany"),
            new User("TestUser3", "User3", "user3@gmail.com",passwordEncoder.encode("password"), "Munich", "Germany"),
            new User("TestUser4", "User4", "user4@gmail.com",passwordEncoder.encode("password"), "Munich", "Germany")
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
        UserFormDTO newUser = new UserFormDTO("test@gmail.com", "Test", "TestUser","Munich", "Germany", "password", "password" );

        Long expectedId = userRepository.findAll().getLast().getId() + 1;
        User expectedUser = new User("TestUser", "Test", "test@gmail.com", passwordEncoder.encode("password"), "Munich", "Germany");
        expectedUser.setId(expectedId);

        UserSimpleDTO userDTO = userService.createUser(newUser);

        assertNotNull(userDTO);
        User notExpectedUser = new User("Test", "TestUser", "test@gmail.com", passwordEncoder.encode("password"), "Munich", "Germany");
        notExpectedUser.setId(-1L);
        UserSimpleDTO notExpected = userMapper.toSimpleDTO(notExpectedUser);
        assertNotEquals(notExpected, userDTO);


        UserSimpleDTO expected = userMapper.toSimpleDTO(expectedUser);
        assertEquals(expected, userDTO);
    }

    @Test
    void testSuccessfulCreateUserNoStudyLocation(){
        UserFormDTO newUser = new UserFormDTO("test@gmail.com", "Test", "TestUser","Munich", null, "password", "password" );

        Long expectedId = userRepository.findAll().getLast().getId() + 1;
        User expectedUser = new User("TestUser", "Test", "test@gmail.com", passwordEncoder.encode("password"), "Munich", null);
        expectedUser.setId(expectedId);

        UserSimpleDTO userDTO = userService.createUser(newUser);

        assertNotNull(userDTO);
        User notExpectedUser = new User("Test", "TestUser", "test@gmail.com", passwordEncoder.encode("password"), "Munich", null);
        notExpectedUser.setId(-1L);
        UserSimpleDTO notExpected = userMapper.toSimpleDTO(notExpectedUser);
        assertNotEquals(notExpected, userDTO);


        UserSimpleDTO expected = userMapper.toSimpleDTO(expectedUser);
        assertEquals(expected, userDTO);
    }

    @Test
    void testEmailAlreadyRegistered(){
        UserFormDTO newUser = new UserFormDTO("user1@gmail.com", "Test", "TestUser", "Munich", "Germany", "password", "password" );

        userService.createUser(newUser);

        UserSimpleDTO userDTO = userService.createUser(newUser);

        assertNull(userDTO.id());
    }

    @Test
    void testPasswordMismatch(){
        UserFormDTO newUser = new UserFormDTO("user1@gmail.com", "Test", "TestUser","Munich", "Germany", "PaSsword", "password" );
        UserSimpleDTO userDTO = userService.createUser(newUser);

        assertNull(userDTO);
    }


    @Test
    void testGetUserInfo(){
        Authentication authentication = new UsernamePasswordAuthenticationToken("user1@gmail.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserSimpleDTO result = userService.getUserInfo();

        User user = new User("TestUser1", "User1", "user1@gmail.com",passwordEncoder.encode("password"), "Munich", "Germany");
        user.setId(result.id());
        UserSimpleDTO expected = userMapper.toSimpleDTO(user);

        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void testGetUnregisteredUserInfo(){
        Authentication authentication = new UsernamePasswordAuthenticationToken("unregistered@gmail.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserSimpleDTO result = userService.getUserInfo();

        assertNull(result);
    }

    @Test
    void testNullAuthentication(){
        SecurityContextHolder.getContext().setAuthentication(null);

        UserSimpleDTO result = userService.getUserInfo();

        assertNull(result);
    }

    @Test
    void testGetUserByIdNotAuthenticated() {
        SecurityContextHolder.getContext().setAuthentication(null);

        UserDTO result = userService.getUserById(1L);

        assertNull(result);
    }

    @Test
    void testGetUserByIdForbidden() {
        userRepository.save(new User("test", "integrationTestUser", "integration@test.com", passwordEncoder.encode("password"), "Munich", "Germany"));
        Authentication authentication = new UsernamePasswordAuthenticationToken("integration@test.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDTO result = userService.getUserById(1L);

        assertNull(result);

    }

    @Test
    void testGetUserByIdSuccess() {
        User user = userRepository.save(new User("test", "integrationTestUser", "integration@test.com", passwordEncoder.encode("password"), "Munich", "Germany"));
        Authentication authentication = new UsernamePasswordAuthenticationToken("integration@test.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDTO result = userService.getUserById(user.getId());

        assertNotNull(result);
        assertEquals(user.getId(), result.id());

    }

    @Test
    void testGetUserByIdNotFound() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("test@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        try{
            userService.getUserById(0L);
        } catch (NoSuchElementException e){
            assert(true);
        }
    }

    @Test
    void testGetUserByIdAdmin() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("testadmin@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDTO result = userService.getUserById(1L);

        assertNotNull(result);
        Long expectedId = 1L;
        assertEquals(expectedId, result.id());
    }
}
