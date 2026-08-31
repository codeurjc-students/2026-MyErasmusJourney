package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.domain.Comment;
import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.*;
import com.myerasmusjourney.backend.enumeration.Category;
import com.myerasmusjourney.backend.mapper.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class ExperienceMapperTest {

    @Spy
    private CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Spy
    private CityMapper cityMapper = Mappers.getMapper(CityMapper.class);

    @InjectMocks
    private ExperienceMapperImpl mapper;

    @Test
    void testToDTOs() {
        User author1 = new User("author1","author1", "author1@email.com", "password", null, null);
        User author2 = new User("author2","author2", "author2@email.com", "password", null, null);

        City city1 = new City("Madrid", "Spain", "description");
        City city2 = new City("Toledo", "Spain", "description");



        List<Experience> experiences = List.of(
                new Experience("Experiencia 1", "Descripcion 1", 9F, null, List.of("Gastronomy", "Documentation"), city1, author1),
                new Experience("Experiencia 2", "Descripcion 2", 8.67F, null, List.of("Transportation", "Documentation"), city2, author2),
                new Experience("Experiencia 3", "Descripcion 3", 5.4F, null, List.of("Gastronomy", "Social_Events"), city1, author1),
                new Experience("Experiencia 4", "Descripcion 4", 0.9F, null, List.of("Culture", "Transportation"), city2, author2)
        );

        List<ExperienceSimpleDTO> expected = List.of(
                new ExperienceSimpleDTO(null, LocalDate.now(),9F, "Experiencia 1", "Descripcion 1", List.of(Category.Gastronomy, Category.Documentation), "Madrid", "Spain", "author1"),
                new ExperienceSimpleDTO(null, LocalDate.now(), 8.67F, "Experiencia 2", "Descripcion 2", List.of(Category.Transportation, Category.Documentation), "Toledo", "Spain", "author2"),
                new ExperienceSimpleDTO(null, LocalDate.now(),5.4F, "Experiencia 3", "Descripcion 3",  List.of(Category.Gastronomy, Category.Social_Events), "Madrid", "Spain", "author1"),
                new ExperienceSimpleDTO(null, LocalDate.now(), 0.9F, "Experiencia 4", "Descripcion 4",  List.of(Category.Culture, Category.Transportation), "Toledo", "Spain", "author2")
        );


        List<ExperienceSimpleDTO> result = mapper.toDTOs(experiences);

        assertEquals(4, result.size());

        for(int i = 0; i< expected.size(); i++){
            ExperienceSimpleDTO res = result.get(i);
            ExperienceSimpleDTO exp = expected.get(i);
            assertEquals(exp.id(), res.id());
            assertEquals(exp.title(), res.title());
            assertEquals(exp.description(), res.description());
            assertEquals(exp.date(), res.date());
            assertEquals(exp.rating(), res.rating());
            assertEquals(exp.categories().size(), res.categories().size());
            assertEquals(exp.cityName(), res.cityName());
            assertEquals(exp.country(), res.country());
            assertEquals(exp.authorName(), res.authorName());
        }

        result = mapper.toDTOs(List.of());

        assertTrue(result.isEmpty());
        assertNotNull(result);
    }

    @Test
    void testToDTO(){
        City city = new City("Madrid", "Spain", "description");
        User user = new User("test", "test", "test@gmail.com", "password", "valencia", "spain");
        Experience exp = new Experience("Title", "Description", 6.8F, null, List.of("Gastronomy", "Documentation"), city, user);
        Comment comment = new Comment("test comment", user, exp);
        exp.addComment(comment);

        CitySimpleDTO citySimpleDTO = new CitySimpleDTO(null, "Madrid", "description", "Spain");
        UserSimpleDTO userSimpleDTO = new UserSimpleDTO(null, "test", "test@gmail.com");
        CommentSimpleDTO commentSimpleDTO = new CommentSimpleDTO(null,LocalDate.now(), "test comment", "test", null);
        ExperienceDTO dto = new ExperienceDTO(null, LocalDate.now(), 6.8F, "Title", "Description",  List.of(Category.Gastronomy, Category.Documentation), citySimpleDTO, userSimpleDTO, List.of(commentSimpleDTO));

        ExperienceDTO result = mapper.toDTO(exp);

        assertEquals(dto.id(), result.id());
        assertEquals(dto.title(), result.title());
        assertEquals(dto.description(), result.description());
        assertEquals(dto.date(), result.date());
        assertEquals(dto.rating(), result.rating());
        assertEquals(dto.categories().size(), result.categories().size());
        assertTrue(result.comments().contains(commentSimpleDTO));

        exp = null;

        result = mapper.toDTO(exp);

        assertNull(result);
    }

    @Test
    void toSimpleDTO_shouldMapExperienceWithCityAndAuthor() {
        City city = new City();
        city.setName("Madrid");
        city.setCountry("Spain");

        User author = new User();
        author.setDisplayName("Author");

        Experience experience = new Experience();
        experience.setId(1L);
        experience.setDate(LocalDate.of(2026, 8, 23));
        experience.setRating(4.5f);
        experience.setTitle("My experience in Madrid");
        experience.setDescription("A wonderful experience");
        experience.setCity(city);
        experience.setAuthor(author);

        ExperienceSimpleDTO result = mapper.toSimpleDTO(experience);

        assertEquals(1L, result.id());
        assertEquals(LocalDate.of(2026, 8, 23), result.date());
        assertEquals(4.5f, result.rating());
        assertEquals("My experience in Madrid", result.title());
        assertEquals("A wonderful experience", result.description());

        assertEquals("Madrid", result.cityName());
        assertEquals("Spain", result.country());
        assertEquals("Author", result.authorName());
    }
}
