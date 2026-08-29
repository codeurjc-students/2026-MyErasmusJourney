package com.myerasmusjourney.backend.integration;

import com.myerasmusjourney.backend.TestDataBase;
import com.myerasmusjourney.backend.domain.Comment;
import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.CommentDTO;
import com.myerasmusjourney.backend.dto.CommentFormDTO;
import com.myerasmusjourney.backend.mapper.CommentMapper;
import com.myerasmusjourney.backend.repository.CommentRepository;
import com.myerasmusjourney.backend.repository.ExperienceRepository;
import com.myerasmusjourney.backend.repository.UserRepository;
import com.myerasmusjourney.backend.service.CommentService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@Tag("integration")
public class CommentServiceTest extends TestDataBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    @BeforeEach
    void setupRepositories(){
        if(commentRepository.count()>0) commentRepository.deleteAll();
        if(userRepository.count()>0) userRepository.deleteAll();
        if(experienceRepository.count()>0) experienceRepository.deleteAll();

        User user = new User();
        user.setEmail("test@email.com");
        user.setDisplayName("Test");
        user = userRepository.save(user);

        Experience experience = new Experience();
        experience.setTitle("title");
        experience.setRating(1.3F);
        experience.setDescription("description");
        experience.setDate(LocalDate.now());
        experience.setCategories(List.of("Personal_Experience"));
        experience.setAuthor(user);
        experience.setCity(null);
        experienceRepository.save(experience);
    }

    @AfterEach
    void deleteComments(){
        if(commentRepository.count()>0) commentRepository.deleteAll();
    }

    @Test
    void testPostComment(){
        Experience experience = experienceRepository.findAll().getFirst();
        User user = userRepository.findByEmail("test@email.com");

        Authentication authentication = new UsernamePasswordAuthenticationToken("test@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CommentDTO commentDTO = commentService.postComment(experience.getId(), new CommentFormDTO("test comment"));

        Comment comment = new Comment("test comment", user, experience);
        comment.setId(commentDTO.id());
        CommentDTO expectedDTO = commentMapper.toDTO(comment);

        assertEquals(expectedDTO, commentDTO);
    }
}
