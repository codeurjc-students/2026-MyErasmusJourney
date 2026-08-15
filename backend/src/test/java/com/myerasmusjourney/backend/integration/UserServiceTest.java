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
        SecurityContextHolder.clearContext();

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @AfterEach
    void deleteUser(){
        userRepository.deleteAll();
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
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
    void testGetUserInfo() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("user1@gmail.com", null, List.of());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User expectedUser = userRepository.findByEmail("user1@gmail.com");

        UserSimpleDTO result = userService.getUserInfo();

        UserSimpleDTO expected = userMapper.toSimpleDTO(expectedUser);

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
        Authentication authentication = new UsernamePasswordAuthenticationToken("user1@gmail.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail("user1@gmail.com");

        UserDTO result = userService.getUserById(user.getId());

        UserDTO expected = userMapper.toDTO(user);

        assertNotNull(result);
        assertEquals(expected, result);

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
        System.out.println("failing test");

        User newAdmin = new User("admin", "TestAdmin", "integrationAdmin@email.com", passwordEncoder.encode("password"), "", "", List.of("USER", "ADMIN"));
        userRepository.save(newAdmin);

        System.out.println("BEFORE SET: " + SecurityContextHolder.getContext().getAuthentication());

        Authentication authentication = new UsernamePasswordAuthenticationToken("integrationAdmin@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println("AFTER SET: " + SecurityContextHolder.getContext().getAuthentication());

        System.out.println("AFTER SET NAME: " + SecurityContextHolder.getContext().getAuthentication().getName());

        User authenticatedUser = userRepository.findByEmail("integrationAdmin@email.com");

        assertNotNull(authenticatedUser);
        assertEquals(newAdmin.getId(), authenticatedUser.getId());
        assertTrue(authenticatedUser.getRoles().contains("ADMIN"));

        User user = userRepository.findByEmail("user1@gmail.com");

        assertNotNull(user);
        assertTrue(user.getId()>0L);
        UserDTO result = userService.getUserById(user.getId());

        UserDTO expected = userMapper.toDTO(user);

        assertNotNull(result); //falla aqui
        assertEquals(expected, result);
    }
}