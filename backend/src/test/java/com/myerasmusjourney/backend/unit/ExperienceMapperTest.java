package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.dto.ExperienceSimpleDTO;
import com.myerasmusjourney.backend.mapper.ExperienceMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExperienceMapperTest {

    private final ExperienceMapper mapper = Mappers.getMapper(ExperienceMapper.class);

    @Test
    void testToDTOs() {

        List<Experience> experiences = List.of(
                new Experience("Experiencia 1", "Descripcion 1", 9F),
                new Experience("Experiencia 2", "Descripcion 2", 8.67F),
                new Experience("Experiencia 3", "Descripcion 3", 5.4F),
                new Experience("Experiencia 4", "Descripcion 4", 0.9F)
        );

        List<ExperienceSimpleDTO> expected = List.of(
                new ExperienceSimpleDTO(null, LocalDate.now(),9F, "Experiencia 1", "Descripcion 1"),
                new ExperienceSimpleDTO(null, LocalDate.now(), 8.67F, "Experiencia 2", "Descripcion 2"),
                new ExperienceSimpleDTO(null, LocalDate.now(),5.4F, "Experiencia 3", "Descripcion 3"),
                new ExperienceSimpleDTO(null, LocalDate.now(), 0.9F, "Experiencia 4", "Descripcion 4")
        );


        List<ExperienceSimpleDTO> result = mapper.toDTOs(experiences);

        assertEquals(4, result.size());

        for(int i = 0; i< expected.size(); i++){
            assertEquals(expected.get(i), result.get(i));
        }

        result = mapper.toDTOs(List.of());

        assertTrue(result.isEmpty());
        assertNotNull(result);
    }
}
