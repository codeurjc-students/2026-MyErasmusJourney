package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.domain.Comment;
import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.CommentDTO;
import com.myerasmusjourney.backend.dto.CommentSimpleDTO;
import com.myerasmusjourney.backend.dto.ExperienceSimpleDTO;
import com.myerasmusjourney.backend.dto.UserSimpleDTO;
import com.myerasmusjourney.backend.enumeration.Category;
import com.myerasmusjourney.backend.mapper.CommentMapper;
import com.myerasmusjourney.backend.mapper.CommentMapperImpl;
import com.myerasmusjourney.backend.mapper.ExperienceMapper;
import com.myerasmusjourney.backend.mapper.UserMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class CommentMapperTest {

    @Spy
    private ExperienceMapper experienceMapper = Mappers.getMapper(ExperienceMapper.class);

    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @InjectMocks
    private CommentMapperImpl mapper;

    @Test
    void toDTO() {
        UserSimpleDTO userSimpleDTO =
                new UserSimpleDTO(2L, "test", "test@email.com");

        ExperienceSimpleDTO experienceSimpleDTO =
                new ExperienceSimpleDTO(
                        3L,
                        LocalDate.now(),
                        1.3F,
                        "title",
                        "description",
                        List.of(Category.Personal_Experience),
                        "Athens",
                        "Greece",
                        "test"
                );

        CommentDTO expectedCommentDTO =
                new CommentDTO(
                        null,
                        LocalDate.now(),
                        "test",
                        userSimpleDTO,
                        experienceSimpleDTO
                );

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

        CommentDTO result = mapper.toDTO(comment);

        assertEquals(expectedCommentDTO.id(), result.id());
        assertEquals(expectedCommentDTO.description(), result.description());
        assertEquals(expectedCommentDTO.experience(), result.experience());
        assertEquals(expectedCommentDTO.author(), result.author());
    }

    @Test
    void toSimpleDTO() {
        User user = new User();
        user.setDisplayName("testAuthor");

        Comment comment = new Comment();
        comment.setId(2L);
        comment.setDate(LocalDate.now());
        comment.setDescription("testing");
        comment.setAuthor(user);

        CommentSimpleDTO expected =
                new CommentSimpleDTO(
                        2L,
                        LocalDate.now(),
                        "testing",
                        "testAuthor"
                );

        CommentSimpleDTO result = mapper.toSimpleDTO(comment);

        assertEquals(expected, result);
    }
}