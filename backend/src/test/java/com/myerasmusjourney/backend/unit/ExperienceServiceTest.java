package com.myerasmusjourney.backend.unit;

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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
public class ExperienceServiceTest {

    @Mock
    private ExperienceRepository experienceRepository;

    @Mock
    private ExperienceMapper experienceMapper;

    @Mock
    private CityService cityService;

    @Mock
    private UserService userService;


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
                new Experience("Experiencia 1", "Descripcion 1", 9F, List.of("Personal_Experience", "Documentation"), null, null),
                new Experience("Experiencia 2", "Descripcion 2", 8.67F, List.of("Social_Events", "Culture"), null, null),
                new Experience("Experiencia 3", "Descripcion 3", 5.4F, List.of("Culture", "Gastronomy"), null, null),
                new Experience("Experiencia 4", "Descripcion 4", 0.9F, List.of("Transportation"), null, null)
        );

        List<ExperienceSimpleDTO> mapped = List.of(
                new ExperienceSimpleDTO(null, LocalDate.now(), 9F, "Experiencia 1", "Descripcion 1", List.of(Category.Personal_Experience, Category.Documentation)),
                new ExperienceSimpleDTO(null, LocalDate.now(), 8.67F, "Experiencia 2", "Descripcion 2", List.of(Category.Social_Events, Category.Culture)),
                new ExperienceSimpleDTO(null, LocalDate.now(), 5.4F, "Experiencia 3", "Descripcion 3", List.of(Category.Culture, Category.Gastronomy)),
                new ExperienceSimpleDTO(null, LocalDate.now(), 0.9F, "Experiencia 4", "Descripcion 4", List.of(Category.Transportation))
        );

        List<ExperienceSimpleDTO> expected = List.of(
                new ExperienceSimpleDTO(null, LocalDate.now(), 9F, "Experiencia 1", "Descripcion 1", List.of(Category.Personal_Experience, Category.Documentation)),
                new ExperienceSimpleDTO(null, LocalDate.now(), 8.67F, "Experiencia 2", "Descripcion 2", List.of(Category.Social_Events, Category.Culture)),
                new ExperienceSimpleDTO(null, LocalDate.now(), 5.4F, "Experiencia 3", "Descripcion 3", List.of(Category.Culture, Category.Gastronomy)),
                new ExperienceSimpleDTO(null, LocalDate.now(), 0.9F, "Experiencia 4", "Descripcion 4", List.of(Category.Transportation))
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

    @Test
    void testGetExperiences(){
        Category[] result = experienceService.getCategories();

        for (Category c: result){
            assertNotNull(Category.valueOf(c.toString()));
        }
    }

    @Test
    void testCreateExperience(){
        ExperienceFormDTO formDTO = new ExperienceFormDTO(
                5.0f,
                "Erasmus party",
                "Great Erasmus party",
                LocalDate.now(),
                List.of("Social_Events", "Studies"),
                1L
        );

        City city = new City(
                "Madrid",
                "Spain",
                "Capital of Spain"
        );
        city.setId(1L);

        User user = new User(
                "Test User",
                "TestUser",
                "test@email.com",
                "encodedPassword",
                "Madrid",
                "Spain"
        );
        user.setId(1L);

        Experience savedExperience = new Experience(
                formDTO.title(),
                formDTO.description(),
                formDTO.rating(),
                formDTO.categories(),
                city,
                user
        );
        savedExperience.setId(1L);

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

        ExperienceDTO expectedDTO = new ExperienceDTO(
                savedExperience.getId(),
                savedExperience.getDate(),
                savedExperience.getRating(),
                savedExperience.getTitle(),
                savedExperience.getDescription(),
                savedExperience.getCategories(),
                cityDTO,
                userDTO
        );

        when(cityService.findById(formDTO.cityId())).thenReturn(city);
        when(userService.getLoggedUser()).thenReturn(user);
        when(experienceRepository.save(any(Experience.class))).thenReturn(savedExperience);
        when(experienceMapper.toDTO(any(Experience.class))).thenReturn(expectedDTO);

        ExperienceDTO result = experienceService.createExperience(formDTO);

        assertNotNull(result);
        assertEquals(expectedDTO, result);

        ArgumentCaptor<Experience> experienceCaptor =
                ArgumentCaptor.forClass(Experience.class);

        verify(experienceRepository).save(experienceCaptor.capture());

        Experience createdExperience = experienceCaptor.getValue();

        assertEquals(formDTO.title(), createdExperience.getTitle());
        assertEquals(formDTO.description(), createdExperience.getDescription());
        assertEquals(formDTO.rating(), createdExperience.getRating());
        assertEquals(formDTO.categories().size(), createdExperience.getCategories().size());
        assertEquals(city, createdExperience.getCity());
        assertEquals(user, createdExperience.getAuthor());

        verify(cityService).findById(formDTO.cityId());
        verify(userService).getLoggedUser();
        verify(experienceRepository).save(any(Experience.class));
        verify(cityService).addExperience(savedExperience, city);
        verify(userService).addExperience(savedExperience, user);
        verify(experienceMapper).toDTO(any(Experience.class));
    }

    @Test
    void testCreateExperienceWithoutCategories(){
        ExperienceFormDTO formDTO = new ExperienceFormDTO(
                5.0f,
                "Erasmus party",
                "Great Erasmus party",
                null,
                List.of(),
                1L
        );

        ExperienceDTO result = experienceService.createExperience(formDTO);
        assertNull(result);
    }

    @Test
    void testCreateExperienceWithToManyCategories(){
        ExperienceFormDTO formDTO = new ExperienceFormDTO(
                5.0f,
                "Erasmus party",
                "Great Erasmus party",
                null,
                List.of("Culture", "Gastronomy", "Social_Events", "Transportation"),
                1L
        );

        ExperienceDTO result = experienceService.createExperience(formDTO);
        assertNull(result);
    }
}
