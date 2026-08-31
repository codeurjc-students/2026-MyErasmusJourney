package com.myerasmusjourney.backend.integration;

import com.myerasmusjourney.backend.TestDataBase;
import com.myerasmusjourney.backend.domain.Comment;
import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.*;
import com.myerasmusjourney.backend.mapper.CommentMapper;
import com.myerasmusjourney.backend.mapper.ExperienceMapper;
import com.myerasmusjourney.backend.mapper.UserMapper;
import com.myerasmusjourney.backend.repository.CommentRepository;
import com.myerasmusjourney.backend.repository.ExperienceRepository;
import com.myerasmusjourney.backend.repository.UserRepository;
import com.myerasmusjourney.backend.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
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
    private ExperienceRepository experienceRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ExperienceMapper experienceMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

        UserDTO userDTO = userService.createUser(newUser);

        assertNotNull(userDTO);
        User notExpectedUser = new User("Test", "TestUser", "test@gmail.com", passwordEncoder.encode("password"), "Munich", "Germany");
        notExpectedUser.setId(-1L);
        UserDTO notExpected = userMapper.toDTO(notExpectedUser);
        assertNotEquals(notExpected, userDTO);


        UserDTO expected = userMapper.toDTO(expectedUser);
        assertEquals(expected, userDTO);
    }

    @Test
    void testSuccessfulCreateUserNoStudyLocation(){
        UserFormDTO newUser = new UserFormDTO("test@gmail.com", "Test", "TestUser","Munich", null, "password", "password" );

        Long expectedId = userRepository.findAll().getLast().getId() + 1;
        User expectedUser = new User("TestUser", "Test", "test@gmail.com", passwordEncoder.encode("password"), "Munich", null);
        expectedUser.setId(expectedId);

        UserDTO userDTO = userService.createUser(newUser);

        assertNotNull(userDTO);
        User notExpectedUser = new User("Test", "TestUser", "test@gmail.com", passwordEncoder.encode("password"), "Munich", null);
        notExpectedUser.setId(-1L);
        UserDTO notExpected = userMapper.toDTO(notExpectedUser);
        assertNotEquals(notExpected, userDTO);


        UserDTO expected = userMapper.toDTO(expectedUser);
        assertEquals(expected, userDTO);
    }

    @Test
    void testEmailAlreadyRegistered(){
        UserFormDTO newUser = new UserFormDTO("user1@gmail.com", "Test", "TestUser", "Munich", "Germany", "password", "password" );

        userService.createUser(newUser);

        UserDTO userDTO = userService.createUser(newUser);

        assertNull(userDTO.id());
    }

    @Test
    void testPasswordMismatch(){
        UserFormDTO newUser = new UserFormDTO("user1@gmail.com", "Test", "TestUser","Munich", "Germany", "Pasword", "password" );
        UserDTO userDTO = userService.createUser(newUser);

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
    @Transactional
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
    @Transactional
    void testGetUserByIdAdmin() {

        User newAdmin = new User("admin", "TestAdmin", "integrationAdmin@email.com", passwordEncoder.encode("password"), "", "", List.of("USER", "ADMIN"));
        userRepository.save(newAdmin);

        Authentication authentication = new UsernamePasswordAuthenticationToken("integrationAdmin@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User authenticatedUser = userRepository.findByEmail("integrationAdmin@email.com");

        assertNotNull(authenticatedUser);
        assertEquals(newAdmin.getId(), authenticatedUser.getId());
        assertTrue(authenticatedUser.getRoles().contains("ADMIN"));

        User user = userRepository.findByEmail("user1@gmail.com");

        assertNotNull(user);
        assertTrue(user.getId()>0L);
        UserDTO result = userService.getUserById(user.getId());

        UserDTO expected = userMapper.toDTO(user);

        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    @Transactional
    void testDeleteUserByIdSuccess() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("user1@gmail.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail("user1@gmail.com");

        UserDTO result = userService.deleteUser(user.getId());

        UserDTO expected = userMapper.toDTO(user);

        assertNotNull(result);
        assertEquals(expected, result);
        assertNull(userRepository.findByEmail(user.getEmail()));
    }

    @Test
    void testDeleteUserByIdNotFound() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("test@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        try{
            userService.deleteUser(0L);
        } catch (NoSuchElementException e){
            assert(true);
        }
    }

    @Test
    @Transactional
    void testDeleteUserByIdAdmin() {

        User newAdmin = new User("admin", "TestAdmin", "integrationAdmin@email.com", passwordEncoder.encode("password"), "", "", List.of("USER", "ADMIN"));
        userRepository.save(newAdmin);

        Authentication authentication = new UsernamePasswordAuthenticationToken("integrationAdmin@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User authenticatedUser = userRepository.findByEmail("integrationAdmin@email.com");

        assertNotNull(authenticatedUser);
        assertEquals(newAdmin.getId(), authenticatedUser.getId());
        assertTrue(authenticatedUser.getRoles().contains("ADMIN"));

        User user = userRepository.findByEmail("user1@gmail.com");

        assertNotNull(user);
        assertTrue(user.getId()>0L);
        UserDTO result = userService.deleteUser(user.getId());

        UserDTO expected = userMapper.toDTO(user);

        assertNotNull(result);
        assertEquals(expected, result);
        assertNull(userRepository.findByEmail(user.getEmail()));
    }

    @Test
    @Transactional
    void testGetUserExperiencesByIdSuccess() {

        Authentication authentication = new UsernamePasswordAuthenticationToken("user1@gmail.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail("user1@gmail.com");

        Experience experience1 = new Experience();
        experience1.setTitle("Experience 1");

        Experience experience2 = new Experience();
        experience2.setTitle("Experience 2");

        experience1 = experienceRepository.save(experience1);
        experience2 = experienceRepository.save(experience2);

        user.addExperience(experience1);
        user.addExperience(experience2);

        user = userRepository.save(user);

        assertNotNull(user);
        assertTrue(user.getId()>0L);

        List<ExperienceSimpleDTO> result = userService.getExperiences(user.getId());

        List<ExperienceSimpleDTO> expected = experienceMapper.toDTOs(List.of(experience1, experience2));

        assertNotNull(result);

        for(int i = 0; i<expected.size(); i++){
            ExperienceSimpleDTO res = result.get(i);
            ExperienceSimpleDTO exp = expected.get(i);
            assertEquals(exp, res);
        }
    }

    @Test
    @Transactional
    void testGetUserExperiencesByIdAdmin() {

        User newAdmin = new User("admin", "TestAdmin", "integrationAdmin@email.com", passwordEncoder.encode("password"), "", "", List.of("USER", "ADMIN"));
        userRepository.save(newAdmin);

        Authentication authentication = new UsernamePasswordAuthenticationToken("integrationAdmin@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User authenticatedUser = userRepository.findByEmail("integrationAdmin@email.com");

        assertNotNull(authenticatedUser);
        assertEquals(newAdmin.getId(), authenticatedUser.getId());
        assertTrue(authenticatedUser.getRoles().contains("ADMIN"));

        User user = userRepository.findByEmail("user1@gmail.com");

        Experience experience1 = new Experience();
        experience1.setTitle("Experience 1");

        Experience experience2 = new Experience();
        experience2.setTitle("Experience 2");

        experience1 = experienceRepository.save(experience1);
        experience2 = experienceRepository.save(experience2);

        user.addExperience(experience1);
        user.addExperience(experience2);

        user = userRepository.save(user);

        assertNotNull(user);
        assertTrue(user.getId()>0L);

        List<ExperienceSimpleDTO> result = userService.getExperiences(user.getId());

        List<ExperienceSimpleDTO> expected = experienceMapper.toDTOs(List.of(experience1, experience2));

        assertNotNull(result);

        for(int i = 0; i<expected.size(); i++){
            ExperienceSimpleDTO res = result.get(i);
            ExperienceSimpleDTO exp = expected.get(i);
            assertEquals(exp, res);
        }
    }

    @Test
    @Transactional
    void testGetUserExperiencesFail(){
        Authentication authentication = new UsernamePasswordAuthenticationToken("user2@gmail.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail("user1@gmail.com");

        Experience experience1 = new Experience();
        experience1.setTitle("Experience 1");

        Experience experience2 = new Experience();
        experience2.setTitle("Experience 2");

        experience1 = experienceRepository.save(experience1);
        experience2 = experienceRepository.save(experience2);

        user.addExperience(experience1);
        user.addExperience(experience2);

        user = userRepository.save(user);

        assertNotNull(user);
        assertTrue(user.getId()>0L);

        List<ExperienceSimpleDTO> result = userService.getExperiences(user.getId());

        assertNull(result);
    }

    @Test
    @Transactional
    void testGetUserCommentsByIdSuccess() {

        Authentication authentication = new UsernamePasswordAuthenticationToken("user1@gmail.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail("user1@gmail.com");

        Comment comment1 = new Comment();
        comment1.setDescription("comment 1");

        Comment comment2 = new Comment();
        comment2.setDescription("comment 2");

        comment1 = commentRepository.save(comment1);
        comment2 = commentRepository.save(comment2);

        user.addComment(comment1);
        user.addComment(comment2);

        user = userRepository.save(user);

        assertNotNull(user);
        assertTrue(user.getId()>0L);

        Collection<CommentSimpleDTO> result = userService.getComments(user.getId());

        Collection<CommentSimpleDTO> expected = commentMapper.toSimpleDTOs(List.of(comment1, comment2));

        assertNotNull(result);


        assertEquals(expected.size(), result.size());
        assertTrue(expected.containsAll(result));

    }

    @Test
    @Transactional
    void testGetUserCommentsByIdAdmin() {

        User newAdmin = new User("admin", "TestAdmin", "integrationAdmin@email.com", passwordEncoder.encode("password"), "", "", List.of("USER", "ADMIN"));
        userRepository.save(newAdmin);

        Authentication authentication = new UsernamePasswordAuthenticationToken("integrationAdmin@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User authenticatedUser = userRepository.findByEmail("integrationAdmin@email.com");

        assertNotNull(authenticatedUser);
        assertEquals(newAdmin.getId(), authenticatedUser.getId());
        assertTrue(authenticatedUser.getRoles().contains("ADMIN"));

        User user = userRepository.findByEmail("user1@gmail.com");

        Comment comment1 = new Comment();
        comment1.setDescription("comment 1");

        Comment comment2 = new Comment();
        comment2.setDescription("comment 2");

        comment1 = commentRepository.save(comment1);
        comment2 = commentRepository.save(comment2);

        user.addComment(comment1);
        user.addComment(comment2);

        user = userRepository.save(user);

        assertNotNull(user);
        assertTrue(user.getId()>0L);

        Collection<CommentSimpleDTO> result = userService.getComments(user.getId());

        Collection<CommentSimpleDTO> expected = commentMapper.toSimpleDTOs(List.of(comment1, comment2));

        assertNotNull(result);

        assertEquals(expected.size(), result.size());
        assertTrue(expected.containsAll(result));
    }

    @Test
    @Transactional
    void testGetUserCommentsFail(){
        Authentication authentication = new UsernamePasswordAuthenticationToken("user2@gmail.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail("user1@gmail.com");

        Comment comment1 = new Comment();
        comment1.setDescription("comment 1");

        Comment comment2 = new Comment();
        comment2.setDescription("comment 2");

        comment1 = commentRepository.save(comment1);
        comment2 = commentRepository.save(comment2);

        user.addComment(comment1);
        user.addComment(comment2);

        user = userRepository.save(user);

        assertNotNull(user);
        assertTrue(user.getId()>0L);

        Collection<CommentSimpleDTO> result = userService.getComments(user.getId());

        assertNull(result);
    }
}