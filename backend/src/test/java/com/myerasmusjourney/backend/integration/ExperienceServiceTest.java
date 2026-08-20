package com.myerasmusjourney.backend.integration;

import com.myerasmusjourney.backend.TestDataBase;
import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.*;
import com.myerasmusjourney.backend.enumeration.Category;
import com.myerasmusjourney.backend.mapper.ExperienceMapper;
import com.myerasmusjourney.backend.repository.ExperienceRepository;
import com.myerasmusjourney.backend.service.CityService;
import com.myerasmusjourney.backend.service.ExperienceService;
import com.myerasmusjourney.backend.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Tag("integration")
public class ExperienceServiceTest extends TestDataBase {

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private ExperienceMapper experienceMapper;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;

    private List<ExperienceSimpleDTO> expected;

    @BeforeEach
    void setup(){

        if (experienceRepository.count() > 0){
            resetDatabase();
        }

        List<Experience> experiences = List.of(
                new Experience("Experiencia 1", "Descripcion 1", 9F, List.of(Category.Studies.name(), Category.Documentation.name()), null, null),
                new Experience("Experiencia 2", "Descripcion 2", 8.67F, List.of(Category.Personal_Experience.name(), Category.Transportation.name()), null, null),
                new Experience("Experiencia 3", "Descripcion 3", 5.4F, List.of(Category.Social_Events.name(), Category.Culture.name()), null, null),
                new Experience("Experiencia 4", "Descripcion 4", 0.9F, List.of(Category.Accommodation.name(), Category.Documentation.name()), null, null)
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

        Experience experience = new Experience("Experience 5", "Descripcion 5", 2.4F, List.of(Category.Personal_Experience.name()),null, null);
        Experience savedExperience = experienceRepository.save(experience);

        ExperienceSimpleDTO savedDTO = new ExperienceSimpleDTO(savedExperience.getId(), savedExperience.getDate(), savedExperience.getRating(), savedExperience.getTitle(), savedExperience.getDescription(), savedExperience.getCategories());
        expected.add(savedDTO);

        result = experienceService.getAllExperiences();

        assertEquals(expected.size(), result.size());
        Long id = result.getFirst().id();
        for(int i = 0; i<expected.size(); i++){
            ExperienceSimpleDTO exp = expected.get(i);
            ExperienceSimpleDTO res = result.get(i);
            assertEquals(exp.id(), res.id());
            assertEquals(exp.description(), res.description());
            assertEquals(exp.title(), res.title());
            assertEquals(exp.date(), res.date());
            assertEquals(exp.rating(), res.rating());
            assertEquals(exp.categories().size(), res.categories().size());
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

    @Test
    void testCreateExperience(){

        Authentication authentication = new UsernamePasswordAuthenticationToken("test@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ExperienceFormDTO formDTO = new ExperienceFormDTO(
                5.0f,
                "Erasmus party",
                "Great Erasmus party",
                LocalDate.of(2022, 1, 13),
                List.of("Social_Events", "Gastronomy"),
                1L
        );

        City city = cityService.findById(1L);
        User user = userService.getLoggedUser();

        Experience savedExperience = new Experience(
                formDTO.title(),
                formDTO.description(),
                formDTO.rating(),
                formDTO.categories(),
                city,
                user
        );

        savedExperience.setDate(formDTO.date());

        CitySimpleDTO cityDTO = new CitySimpleDTO(
                city.getId(),
                city.getName(),
                city.getDescription(),
                city.getCountry()
        );

        UserSimpleDTO userDTO = new UserSimpleDTO(
                user.getId(),
                user.getDisplayName(),
                user.getEmail()
        );

        ExperienceDTO result = experienceService.createExperience(formDTO);

        ExperienceDTO expectedDTO = new ExperienceDTO(
                result.id(),
                savedExperience.getDate(),
                savedExperience.getRating(),
                savedExperience.getTitle(),
                savedExperience.getDescription(),
                savedExperience.getCategories(),
                cityDTO,
                userDTO
        );

        assertNotNull(result);

        assertEquals(expectedDTO.id(), result.id());
        assertEquals(expectedDTO.description(), result.description());
        assertEquals(expectedDTO.title(), result.title());
        assertEquals(expectedDTO.date(), result.date());
        assertEquals(expectedDTO.rating(), result.rating());
        assertEquals(expectedDTO.city(), result.city());
        assertEquals(expectedDTO.author(), result.author());
        assertEquals(expectedDTO.categories().size(), result.categories().size());

        assertEquals(formDTO.title(), result.title());
        assertEquals(formDTO.description(), result.description());
        assertEquals(formDTO.rating(), result.rating());
        assertEquals(formDTO.categories().size(), result.categories().size());
        assertEquals(cityDTO, result.city());
        assertEquals(userDTO, result.author());
    }

    @Test
    void testCreateExperienceCityDoesntExist(){
        Authentication authentication = new UsernamePasswordAuthenticationToken("test@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ExperienceFormDTO formDTO = new ExperienceFormDTO(
                5.0f,
                "Erasmus party",
                "Great Erasmus party",
                null,
                List.of("Social_Events"),
                0L
        );

        try{
            experienceService.createExperience(formDTO);
        } catch (NoSuchElementException exception){
            assertTrue(true);
        }
    }
}
