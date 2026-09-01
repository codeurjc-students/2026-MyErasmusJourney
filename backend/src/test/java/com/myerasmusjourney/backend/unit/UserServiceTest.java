package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.Comment;
import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.*;
import com.myerasmusjourney.backend.mapper.CommentMapper;
import com.myerasmusjourney.backend.mapper.ExperienceMapper;
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

import java.util.Collection;
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
    private ExperienceMapper experienceMapper;

    @Mock
    private CommentMapper commentMapper;

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

        UserDTO DTO = new UserDTO(1L, "Test", "TestUser", "test@gmail.com", "Munich, Germany", List.of("USER"), List.of(), List.of());
        when(userMapper.toDTO(any(User.class))).thenReturn(DTO);

        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

        UserDTO userDTO = userService.createUser(newUser);
        Long expectedId = 1L;
        assertEquals(expectedId, userDTO.id());
        assertEquals(DTO, userDTO);

        verify(userRepository).findByEmail(newUser.email());
        verify(userMapper).toDTO(any(User.class));
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testEmailAlreadyRegistered(){
        UserFormDTO newUser = new UserFormDTO("test@gmail.com", "Test", "TestUser","Munich", "Germany", "password", "password" );
        User user = new User("Test", "TestUser", "test@gmail.com", "password", "Munich", "Germany");
        when(userRepository.findByEmail(newUser.email())).thenReturn(user);

        UserDTO DTO = new UserDTO(null, "Test", "TestUser","test@gmail.com","Munich, Germany", List.of("USER"), List.of(), List.of());
        when(userMapper.toDTO(any(User.class))).thenReturn(DTO);

        UserDTO userDTO = userService.createUser(newUser);
        assertNull(userDTO.id());

        verify(userRepository).findByEmail(newUser.email());
        verify(userMapper).toDTO(any(User.class));

    }

    @Test
    void testPasswordMismatch(){
        UserFormDTO newUser = new UserFormDTO("test@gmail.com", "Test", "TestUser","Germany", "Munich", "password", "pAssword" );

        UserDTO userDTO = userService.createUser(newUser);
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
                List.of("USER"),
                List.of(),
                List.of()
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
                List.of("USER"),
                List.of(),
                List.of()
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
                List.of("USER"),
                List.of(),
                List.of()
        );

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("admin@email.com");

        when(userRepository.findByEmail("admin@email.com")).thenReturn(admin);

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

    @Test
    void testDeleteUserByIdSuccess() {
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
                List.of("USER"),
                List.of(),
                List.of()
        );

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@email.com");

        when(userRepository.findByEmail("test@email.com")).thenReturn(loggedUser);

        when(userRepository.findById(1L)).thenReturn(Optional.of(targetUser));

        when(userMapper.toDTO(targetUser)).thenReturn(dto);

        UserDTO result = userService.deleteUser(1L);

        assertEquals(dto, result);

        verify(userRepository).findByEmail("test@email.com");
        verify(userRepository).findById(1L);
        verify(userMapper).toDTO(targetUser);
    }

    @Test
    void testDeleteUserByIdNotFound() {
        User loggedUser = new User("Test", "user", "test@email.com", "password", "Munich", "Germany");
        loggedUser.setId(1L);

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@email.com");

        when(userRepository.findByEmail("test@email.com")).thenReturn(loggedUser);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.deleteUser(1L));

        verify(userRepository).findById(1L);
        verifyNoInteractions(userMapper);
    }

    @Test
    void testDeleteUserByIdAdmin() {
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
                List.of("USER"),
                List.of(),
                List.of()
        );

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("admin@email.com");

        when(userRepository.findByEmail("admin@email.com")).thenReturn(admin);

        when(userRepository.findById(1L)).thenReturn(Optional.of(targetUser));

        when(userMapper.toDTO(targetUser)).thenReturn(dto);

        UserDTO result = userService.deleteUser(1L);

        assertEquals(dto, result);

        verify(userRepository).findByEmail("admin@email.com");
        verify(userRepository).findById(1L);
        verify(userMapper).toDTO(targetUser);
    }

    @Test
    void testGetUserExperiencesByUser(){
        User user = new User();
        user.setId(1L);
        user.setEmail("user@email.com");

        Experience experience1 = new Experience();
        experience1.setTitle("Experience 1");
        experience1.setId(1L);

        Experience experience2 = new Experience();
        experience2.setTitle("Experience 2");
        experience2.setId(2L);

        user.addExperience(experience1);
        user.addExperience(experience2);

        List<ExperienceSimpleDTO> expected = List.of(
                new ExperienceSimpleDTO(1L, null, null, "Experience 1", null, null, null, null, null),
                new ExperienceSimpleDTO(2L, null, null, "Experience 2", null, null, null, null, null)

        );

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user@email.com");
        when(userRepository.findByEmail("user@email.com")).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(experienceMapper.toDTOs(argThat(experiences -> experiences.size() == 2))).thenReturn(expected);

        List<ExperienceSimpleDTO> result = userService.getExperiences(1L);

        for(int i = 0; i<expected.size(); i++){
            ExperienceSimpleDTO res = result.get(i);
            ExperienceSimpleDTO exp = expected.get(i);
            assertEquals(exp, res);
        }

        verify(userRepository).findByEmail("user@email.com");
        verify(userRepository).findById(1L);
        verify(experienceMapper).toDTOs(argThat(experiences -> experiences.size() == 2));
    }

    @Test
    void testGetUserExperiencesByAdmin(){
        User user = new User();
        user.setId(1L);
        user.setEmail("user@email.com");

        Experience experience1 = new Experience();
        experience1.setTitle("Experience 1");
        experience1.setId(1L);

        Experience experience2 = new Experience();
        experience2.setTitle("Experience 2");
        experience2.setId(2L);

        user.addExperience(experience1);
        user.addExperience(experience2);

        User admin = new User();
        admin.setEmail("admin@email.com");
        admin.setId(2L);
        admin.setRoles(List.of("USER", "ADMIN"));

        List<ExperienceSimpleDTO> expected = List.of(
                new ExperienceSimpleDTO(1L, null, null, "Experience 1", null, null, null, null, null),
                new ExperienceSimpleDTO(2L, null, null, "Experience 2", null, null, null, null, null)

        );

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("admin@email.com");
        when(userRepository.findByEmail("admin@email.com")).thenReturn(admin);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(experienceMapper.toDTOs(argThat(experiences -> experiences.size() == 2))).thenReturn(expected);

        List<ExperienceSimpleDTO> result = userService.getExperiences(1L);

        for(int i = 0; i<expected.size(); i++){
            ExperienceSimpleDTO res = result.get(i);
            ExperienceSimpleDTO exp = expected.get(i);
            assertEquals(exp, res);
        }

        verify(userRepository).findByEmail("admin@email.com");
        verify(userRepository).findById(1L);
        verify(experienceMapper).toDTOs(argThat(experiences -> experiences.size() == 2));
    }

    @Test
    void testGetUserExperiencesFail(){
        User user = new User();
        user.setId(1L);
        user.setEmail("user@email.com");

        Experience experience1 = new Experience();
        experience1.setTitle("Experience 1");
        experience1.setId(1L);

        Experience experience2 = new Experience();
        experience2.setTitle("Experience 2");
        experience2.setId(2L);

        user.addExperience(experience1);
        user.addExperience(experience2);

        User user2 = new User();
        user2.setEmail("user2@email.com");
        user2.setId(2L);
        user2.setRoles(List.of("USER"));

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user2@email.com");
        when(userRepository.findByEmail("user2@email.com")).thenReturn(user2);

        List<ExperienceSimpleDTO> result = userService.getExperiences(1L);

        assertNull(result);

        verify(userRepository).findByEmail("user2@email.com");
    }

    @Test
    void testGetUserCommentsByUser(){
        User user = new User();
        user.setId(1L);
        user.setEmail("user@email.com");

        Comment comment1 = new Comment();
        comment1.setDescription("Comment 1");
        comment1.setId(1L);

        Comment comment2 = new Comment();
        comment2.setDescription("Comment 2");
        comment2.setId(2L);

        user.addComment(comment1);
        user.addComment(comment2);

        List<CommentSimpleDTO> expected = List.of(
                new CommentSimpleDTO(1L, null, "Comment 1", null, 4L),
                new CommentSimpleDTO(2L, null, "Comment 2", null, 5L)

        );

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user@email.com");
        when(userRepository.findByEmail("user@email.com")).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(commentMapper.toSimpleDTOs(argThat(comments -> comments.size() == 2))).thenReturn(expected);

        Collection<CommentSimpleDTO> result = userService.getComments(1L);

        assertEquals(expected.size(), result.size());

        int i = 0;
        for(CommentSimpleDTO res: result){
            CommentSimpleDTO exp = expected.get(i);
            assertEquals(exp, res);
            i++;
        }

        verify(userRepository).findByEmail("user@email.com");
        verify(userRepository).findById(1L);
        verify(commentMapper).toSimpleDTOs(argThat(comments -> comments.size() == 2));
    }

    @Test
    void testGetUserCommentsByAdmin(){
        User user = new User();
        user.setId(1L);
        user.setEmail("user@email.com");

        Comment comment1 = new Comment();
        comment1.setDescription("Comment 1");
        comment1.setId(1L);

        Comment comment2 = new Comment();
        comment2.setDescription("Comment 2");
        comment2.setId(2L);

        user.addComment(comment1);
        user.addComment(comment2);



        User admin = new User();
        admin.setEmail("admin@email.com");
        admin.setId(2L);
        admin.setRoles(List.of("USER", "ADMIN"));

        List<CommentSimpleDTO> expected = List.of(
                new CommentSimpleDTO(1L, null, "Comment 1", null, 22L),
                new CommentSimpleDTO(2L, null, "Comment 2", null, 3L)

        );

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("admin@email.com");
        when(userRepository.findByEmail("admin@email.com")).thenReturn(admin);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(commentMapper.toSimpleDTOs(argThat(comments -> comments.size() == 2))).thenReturn(expected);

        Collection<CommentSimpleDTO> result = userService.getComments(1L);

        assertEquals(expected.size(), result.size());

        int i = 0;
        for(CommentSimpleDTO res: result){
            CommentSimpleDTO exp = expected.get(i);
            assertEquals(exp, res);
            i++;
        }

        verify(userRepository).findByEmail("admin@email.com");
        verify(userRepository).findById(1L);
        verify(commentMapper).toSimpleDTOs(argThat(comments -> comments.size() == 2));
    }

    @Test
    void testGetUserCommentsFail(){
        User user = new User();
        user.setId(1L);
        user.setEmail("user@email.com");

        Comment comment1 = new Comment();
        comment1.setDescription("Comment 1");
        comment1.setId(1L);

        Comment comment2 = new Comment();
        comment2.setDescription("Comment 2");
        comment2.setId(2L);

        user.addComment(comment1);
        user.addComment(comment2);

        User user2 = new User();
        user2.setEmail("user2@email.com");
        user2.setId(2L);
        user2.setRoles(List.of("USER"));

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("user2@email.com");
        when(userRepository.findByEmail("user2@email.com")).thenReturn(user2);

        List<ExperienceSimpleDTO> result = userService.getExperiences(1L);

        assertNull(result);

        verify(userRepository).findByEmail("user2@email.com");
    }
}
