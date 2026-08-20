package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.CitySimpleDTO;
import com.myerasmusjourney.backend.dto.ExperienceDTO;
import com.myerasmusjourney.backend.dto.ExperienceSimpleDTO;
import com.myerasmusjourney.backend.dto.UserSimpleDTO;
import com.myerasmusjourney.backend.enumeration.Category;
import com.myerasmusjourney.backend.mapper.ExperienceMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
public class ExperienceMapperTest {

    private final ExperienceMapper mapper = Mappers.getMapper(ExperienceMapper.class);

    @Test
    void testToDTOs() {

        List<Experience> experiences = List.of(
                new Experience("Experiencia 1", "Descripcion 1", 9F, null, List.of("Gastronomy", "Documentation"), null, null),
                new Experience("Experiencia 2", "Descripcion 2", 8.67F, null, List.of("Transportation", "Documentation"), null, null),
                new Experience("Experiencia 3", "Descripcion 3", 5.4F, null, List.of("Gastronomy", "Social_Events"), null, null),
                new Experience("Experiencia 4", "Descripcion 4", 0.9F, null, List.of("Culture", "Transportation"), null, null)
        );

        List<ExperienceSimpleDTO> expected = List.of(
                new ExperienceSimpleDTO(null, LocalDate.now(),9F, "Experiencia 1", "Descripcion 1", List.of(Category.Gastronomy, Category.Documentation)),
                new ExperienceSimpleDTO(null, LocalDate.now(), 8.67F, "Experiencia 2", "Descripcion 2", List.of(Category.Transportation, Category.Documentation)),
                new ExperienceSimpleDTO(null, LocalDate.now(),5.4F, "Experiencia 3", "Descripcion 3",  List.of(Category.Gastronomy, Category.Social_Events)),
                new ExperienceSimpleDTO(null, LocalDate.now(), 0.9F, "Experiencia 4", "Descripcion 4",  List.of(Category.Culture, Category.Transportation))
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

        CitySimpleDTO citySimpleDTO = new CitySimpleDTO(null, "Madrid", "description", "Spain");
        UserSimpleDTO userSimpleDTO = new UserSimpleDTO(null, "test", "test@gmail.com");
        ExperienceDTO dto = new ExperienceDTO(null, LocalDate.now(), 6.8F, "Title", "Description",  List.of(Category.Gastronomy, Category.Documentation), citySimpleDTO, userSimpleDTO);

        ExperienceDTO result = mapper.toDTO(exp);

        assertEquals(dto, result);

        exp = null;

        result = mapper.toDTO(exp);

        assertNull(result);
    }
}
