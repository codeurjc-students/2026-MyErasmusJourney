package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.domain.Comment;
import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.CommentDTO;
import com.myerasmusjourney.backend.dto.CommentFormDTO;
import com.myerasmusjourney.backend.dto.ExperienceSimpleDTO;
import com.myerasmusjourney.backend.dto.UserSimpleDTO;
import com.myerasmusjourney.backend.enumeration.Category;
import com.myerasmusjourney.backend.mapper.CommentMapper;
import com.myerasmusjourney.backend.repository.CommentRepository;
import com.myerasmusjourney.backend.service.CommentService;
import com.myerasmusjourney.backend.service.ExperienceService;
import com.myerasmusjourney.backend.service.UserService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private UserService userService;

    @Mock
    private ExperienceService experienceService;

    @InjectMocks
    private CommentService commentService;

    @Test
    void testPostComment(){
        UserSimpleDTO userSimpleDTO = new UserSimpleDTO(2L, "test", "test@email.com");
        ExperienceSimpleDTO experienceSimpleDTO = new ExperienceSimpleDTO(3L, LocalDate.now(), 1.3F, "title", "description", List.of(Category.Personal_Experience), "Athens", "Greece", "test");
        CommentDTO expectedCommentDTO = new CommentDTO(1L, LocalDate.now(), "test", userSimpleDTO, experienceSimpleDTO);

        City city = new City();
        city.setName("Athens");
        city.setCountry("Greece");

        User user = new User();
        user.setId(2L);
        user.setEmail("test@email.com");
        user.setDisplayName("test");

        Experience experience = new Experience();
        experience.setId(3L);
        experience.setTitle("title");
        experience.setRating(1.3F);
        experience.setDescription("description");
        experience.setDate(LocalDate.now());
        experience.setCategories(List.of("Personal_Experience"));
        experience.setAuthor(user);
        experience.setCity(city);

        Comment comment = new Comment("test", user, experience);

        when(experienceService.getExperience(1L)).thenReturn(experience);
        when(userService.getLoggedUser()).thenReturn(user);
        when(commentMapper.toDTO(any(Comment.class))).thenReturn(expectedCommentDTO);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentDTO result = commentService.postComment(1L, new CommentFormDTO("test"));

        assertEquals(expectedCommentDTO, result);

        verify(experienceService).getExperience(1L);
        verify(userService).getLoggedUser();
        verify(commentMapper).toDTO(any(Comment.class));
        verify(commentRepository).save(any(Comment.class));
    }
}
