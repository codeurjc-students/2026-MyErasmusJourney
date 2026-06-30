package com.myerasmusjourney.backend.integration;

import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.dto.ExperienceSimpleDTO;
import com.myerasmusjourney.backend.mapper.ExperienceMapper;
import com.myerasmusjourney.backend.repository.ExperienceRepository;
import com.myerasmusjourney.backend.service.ExperienceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Tag("integration")
public class ExperienceServiceTest {

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private ExperienceMapper experienceMapper;

    private List<ExperienceSimpleDTO> expected;

    @BeforeEach
    void setup(){

        if (experienceRepository.count() > 0){
            resetDatabase();
        }

        List<Experience> experiences = List.of(
                new Experience("Experiencia 1", "Descripcion 1", 9F),
                new Experience("Experiencia 2", "Descripcion 2", 8.67F),
                new Experience("Experiencia 3", "Descripcion 3", 5.4F),
                new Experience("Experiencia 4", "Descripcion 4", 0.9F)
        );
        experienceRepository.saveAll(experiences);

        this.expected = experienceMapper.toDTOs(experiences);
    }

    @AfterEach
    void resetDatabase(){
        experienceRepository.deleteAll();
    }

    @Test
    void testGetAll(){
        List<ExperienceSimpleDTO> result = experienceService.getAllExperiences();

        assertEquals(expected.size(), result.size());
        Long id = result.getFirst().id();
        for(int i = 0; i<expected.size(); i++){
            ExperienceSimpleDTO exp = expected.get(i);
            ExperienceSimpleDTO res = result.get(i);
            assert(exp.equals(res));
            assertEquals(id, res.id());
            id++;
        }

    }

    @Test
    void testGetAllDynamic(){
        List<ExperienceSimpleDTO> result = experienceService.getAllExperiences();

        assertEquals(expected.size(), result.size());

        Experience experience = new Experience("Experience 5", "Descripcion 5", 2.4F);
        Experience savedExperience = experienceRepository.save(experience);

        ExperienceSimpleDTO savedDTO = new ExperienceSimpleDTO(savedExperience.getId(), savedExperience.getDate(), savedExperience.getRating(), savedExperience.getTitle(), savedExperience.getDescription());
        expected.add(savedDTO);

        result = experienceService.getAllExperiences();

        assertEquals(expected.size(), result.size());
        Long id = result.getFirst().id();
        for(int i = 0; i<expected.size(); i++){
            ExperienceSimpleDTO exp = expected.get(i);
            ExperienceSimpleDTO res = result.get(i);
            assert(exp.equals(res));
            assertEquals(id, res.id());
            id++;
        }
    }

    @Test
    void testGetAllEmpty() {
        experienceRepository.deleteAll();

        List<ExperienceSimpleDTO> result = experienceService.getAllExperiences();

        assertTrue(result.isEmpty());
    }
}
