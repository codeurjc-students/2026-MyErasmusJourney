package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.dto.ExperienceSimpleDTO;
import com.myerasmusjourney.backend.mapper.ExperienceMapper;
import com.myerasmusjourney.backend.repository.ExperienceRepository;
import com.myerasmusjourney.backend.service.ExperienceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExperienceServiceTest {

    @Mock
    private ExperienceRepository experienceRepository;

    @Mock
    private ExperienceMapper experienceMapper;

    @InjectMocks
    private ExperienceService experienceService;

    @Test
    void testGetEmptyExperiences(){
        List<Experience> experiences = List.of();

        when(experienceRepository.findAll()).thenReturn(experiences); //simulates what should happen when repository.findAll() is called in the real code.
        when(experienceMapper.toDTOs(experiences)).thenReturn(List.of());


        List<ExperienceSimpleDTO> result = experienceService.getAllExperiences();

        assertEquals(List.of(), result);

        verify(experienceRepository).findAll(); //verifies repository.findAll() has been called
        verify(experienceMapper).toDTOs(experiences);

    }

    @Test
    void testGetAllExperiences(){
        List<Experience> experiences = List.of(
                new Experience("Experiencia 1", "Descripcion 1", 9F),
                new Experience("Experiencia 2", "Descripcion 2", 8.67F),
                new Experience("Experiencia 3", "Descripcion 3", 5.4F),
                new Experience("Experiencia 4", "Descripcion 4", 0.9F)
        );

        List<ExperienceSimpleDTO> mapped = List.of(
                new ExperienceSimpleDTO(null, LocalDate.now(), 9F, "Experiencia 1", "Descripcion 1"),
                new ExperienceSimpleDTO(null, LocalDate.now(), 8.67F, "Experiencia 2", "Descripcion 2"),
                new ExperienceSimpleDTO(null, LocalDate.now(), 5.4F, "Experiencia 3", "Descripcion 3"),
                new ExperienceSimpleDTO(null, LocalDate.now(), 0.9F, "Experiencia 4", "Descripcion 4")
        );

        List<ExperienceSimpleDTO> expected = List.of(
                new ExperienceSimpleDTO(null, LocalDate.now(), 9F, "Experiencia 1", "Descripcion 1"),
                new ExperienceSimpleDTO(null, LocalDate.now(), 8.67F, "Experiencia 2", "Descripcion 2"),
                new ExperienceSimpleDTO(null, LocalDate.now(), 5.4F, "Experiencia 3", "Descripcion 3"),
                new ExperienceSimpleDTO(null, LocalDate.now(), 0.9F, "Experiencia 4", "Descripcion 4")
        );

        when(experienceRepository.findAll()).thenReturn(experiences);
        when(experienceMapper.toDTOs(experiences)).thenReturn(mapped);

        List<ExperienceSimpleDTO> result = experienceService.getAllExperiences();

        assertEquals(expected.size(), result.size());

        for(int i = 0; i<expected.size(); i++){
            ExperienceSimpleDTO exp = expected.get(i);
            ExperienceSimpleDTO res = result.get(i);
            assertEquals(exp, res);
        }

        verify(experienceRepository).findAll();
        verify(experienceMapper).toDTOs(experiences);

    }
}
