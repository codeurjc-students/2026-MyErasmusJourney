package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.domain.Comment;
import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.CommentDTO;
import com.myerasmusjourney.backend.dto.ExperienceSimpleDTO;
import com.myerasmusjourney.backend.dto.UserSimpleDTO;
import com.myerasmusjourney.backend.enumeration.Category;
import com.myerasmusjourney.backend.mapper.CommentMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Tag("unit")
public class CommentMapperTest {

    private final CommentMapper mapper = Mappers.getMapper(CommentMapper.class);

    @Test
    void toDTO(){
        UserSimpleDTO userSimpleDTO = new UserSimpleDTO(2L, "test", "test@email.com");
        ExperienceSimpleDTO experienceSimpleDTO = new ExperienceSimpleDTO(3L, LocalDate.now(), 1.3F, "title", "description", List.of(Category.Personal_Experience), "Athens", "Greece", "test");
        CommentDTO expectedCommentDTO = new CommentDTO(null, LocalDate.now(), "test", userSimpleDTO, experienceSimpleDTO);

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
        assertEquals(expectedCommentDTO.date(), result.date());
        assertEquals(expectedCommentDTO.description(), result.description());
        assertEquals(expectedCommentDTO.experience(), result.experience());
        assertEquals(expectedCommentDTO.author(), result.author());
    }

}
