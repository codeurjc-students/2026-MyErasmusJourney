package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.controller.ExperienceRestController;
import com.myerasmusjourney.backend.dto.ExperienceSimpleDTO;
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
public class ExperienceRestControllerTest {

    @Mock
    private ExperienceService experienceService;

    @InjectMocks
    private ExperienceRestController experienceRestController;

    @Test
    void testGetExperiences(){
        List<ExperienceSimpleDTO> mapped = List.of(
                new ExperienceSimpleDTO(null, LocalDate.now(), 6F, "Experiencia 1", "Descripcion 1"),
                new ExperienceSimpleDTO(null, LocalDate.now(), 8.67F, "Experiencia 2", "Descripcion 2"),
                new ExperienceSimpleDTO(null, LocalDate.now(), 5.1F, "Experiencia 3", "Descripcion 3"),
                new ExperienceSimpleDTO(null, LocalDate.now(), 1.9F, "Experiencia 4", "Descripcion 4")
        );

        List<ExperienceSimpleDTO> expected = List.of(
                new ExperienceSimpleDTO(null, LocalDate.now(), 6F, "Experiencia 1", "Descripcion 1"),
                new ExperienceSimpleDTO(null, LocalDate.now(), 8.67F, "Experiencia 2", "Descripcion 2"),
                new ExperienceSimpleDTO(null, LocalDate.now(), 5.1F, "Experiencia 3", "Descripcion 3"),
                new ExperienceSimpleDTO(null, LocalDate.now(), 1.9F, "Experiencia 4", "Descripcion 4")
        );

        when(experienceService.getAllExperiences()).thenReturn(mapped);

        List<ExperienceSimpleDTO> result = experienceRestController.getExperiences();

        assertEquals(expected.size(), result.size());

        for(int i = 0; i< expected.size(); i++){
            assertEquals(expected.get(i), result.get(i));
        }

        verify(experienceService).getAllExperiences();
    }
}
