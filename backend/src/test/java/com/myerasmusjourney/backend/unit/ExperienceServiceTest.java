package com.myerasmusjourney.backend.unit;

import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.domain.Comment;
import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.*;
import com.myerasmusjourney.backend.enumeration.Category;
import com.myerasmusjourney.backend.mapper.CommentMapper;
import com.myerasmusjourney.backend.mapper.ExperienceMapper;
import com.myerasmusjourney.backend.repository.ExperienceRepository;
import com.myerasmusjourney.backend.service.CityService;
import com.myerasmusjourney.backend.service.ExperienceService;
import com.myerasmusjourney.backend.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag("unit")
public class ExperienceServiceTest {

    @Mock
    private ExperienceRepository experienceRepository;

    @Mock
    private ExperienceMapper experienceMapper;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private CityService cityService;

    @Mock
    private UserService userService;


    @InjectMocks
    private ExperienceService experienceService;

    @Test
    void testGetEmptyExperiences() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Experience> emptyPage = new PageImpl<>(List.of(), pageable, 0);

        when(experienceRepository.findAll(pageable)).thenReturn(emptyPage);

        Page<ExperienceSimpleDTO> result =
                experienceService.getAllExperiences(pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getTotalPages());

        verify(experienceRepository).findAll(pageable);
        verifyNoInteractions(experienceMapper);
    }

    @Test
    void testGetAllExperiences() {
        Pageable pageable = PageRequest.of(0, 10);

        List<Experience> experiences = List.of(
                new Experience(
                        "Experiencia 1",
                        "Descripcion 1",
                        9F,
                        null,
                        List.of("Personal_Experience", "Documentation"),
                        null,
                        null
                ),
                new Experience(
                        "Experiencia 2",
                        "Descripcion 2",
                        8.67F,
                        null,
                        List.of("Social_Events", "Culture"),
                        null,
                        null
                ),
                new Experience(
                        "Experiencia 3",
                        "Descripcion 3",
                        5.4F,
                        null,
                        List.of("Culture", "Gastronomy"),
                        null,
                        null
                ),
                new Experience(
                        "Experiencia 4",
                        "Descripcion 4",
                        0.9F,
                        null,
                        List.of("Transportation"),
                        null,
                        null
                )
        );

        Page<Experience> experiencePage = new PageImpl<>(
                experiences,
                pageable,
                experiences.size()
        );

        List<ExperienceSimpleDTO> expected = List.of(
                new ExperienceSimpleDTO(
                        null,
                        LocalDate.now(),
                        9F,
                        "Experiencia 1",
                        "Descripcion 1",
                        List.of(Category.Personal_Experience, Category.Documentation),
                        "London",
                        "United Kingdom",
                        "user"
                ),
                new ExperienceSimpleDTO(
                        null,
                        LocalDate.now(),
                        8.67F,
                        "Experiencia 2",
                        "Descripcion 2",
                        List.of(Category.Social_Events, Category.Culture),
                        "Berlin",
                        "Germany",
                        "user2"
                ),
                new ExperienceSimpleDTO(
                        null,
                        LocalDate.now(),
                        5.4F,
                        "Experiencia 3",
                        "Descripcion 3",
                        List.of(Category.Culture, Category.Gastronomy),
                        "London",
                        "United Kingdom",
                        "user"
                ),
                new ExperienceSimpleDTO(
                        null,
                        LocalDate.now(),
                        0.9F,
                        "Experiencia 4",
                        "Descripcion 4",
                        List.of(Category.Transportation),
                        "Berlin",
                        "Germany",
                        "user2"
                )
        );

        when(experienceRepository.findAll(pageable))
                .thenReturn(experiencePage);

        when(experienceMapper.toSimpleDTO(experiences.get(0)))
                .thenReturn(expected.get(0));
        when(experienceMapper.toSimpleDTO(experiences.get(1)))
                .thenReturn(expected.get(1));
        when(experienceMapper.toSimpleDTO(experiences.get(2)))
                .thenReturn(expected.get(2));
        when(experienceMapper.toSimpleDTO(experiences.get(3)))
                .thenReturn(expected.get(3));

        Page<ExperienceSimpleDTO> result =
                experienceService.getAllExperiences(pageable);

        assertNotNull(result);

        // Contenido
        assertEquals(expected.size(), result.getNumberOfElements());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), result.getContent().get(i));
        }

        // Información de paginación
        assertEquals(0, result.getNumber());
        assertEquals(10, result.getSize());
        assertEquals(4, result.getTotalElements());
        assertEquals(1, result.getTotalPages());

        verify(experienceRepository).findAll(pageable);

        verify(experienceMapper).toSimpleDTO(experiences.get(0));
        verify(experienceMapper).toSimpleDTO(experiences.get(1));
        verify(experienceMapper).toSimpleDTO(experiences.get(2));
        verify(experienceMapper).toSimpleDTO(experiences.get(3));
    }

    @Test
    void testGetCategories(){
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
                null,
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
                userDTO,
                List.of()
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

    @Test
    void testGetExperienceById() {
        Experience experience =  new Experience("Experiencia 1", "Descripcion 1", 9F, null, List.of("Personal_Experience", "Documentation"), null, null);

        ExperienceDTO experienceDTO = new ExperienceDTO(null, LocalDate.now(), 9F, "Experiencia 1", "Descripcion 1", List.of(Category.Personal_Experience, Category.Documentation), null, null, List.of());
        Long id = 1L;

        when(experienceRepository.findById(id)).thenReturn(Optional.of(experience));

        when(experienceMapper.toDTO(experience))
                .thenReturn(experienceDTO);

        ExperienceDTO result = experienceService.getExperienceById(1L);

        Assert.assertEquals(experienceDTO, result);

        verify(experienceRepository).findById(1L);
        verify(experienceMapper).toDTO(experience);
    }

    @Test
    void testGetExperienceByIdNotFound() {
        Long id = 0L;

        when(experienceRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> experienceService.getExperienceById(id));
    }

    @Test
    void testGetComments(){
        Experience experience = new Experience();
        experience.addComment(new Comment());
        experience.addComment(new Comment());

        when(experienceRepository.findById(1L)).thenReturn(Optional.of(experience));
        when(commentMapper.toSimpleDTOs(argThat(comments -> comments.size() == 2))).thenReturn(List.of(new CommentSimpleDTO(null, null, null, null), new CommentSimpleDTO(null, null, null, null)));
        Collection<CommentSimpleDTO> result = experienceService.getComments(1L);

        assertEquals(2, result.size());

        verify(experienceRepository).findById(1L);
        verify(commentMapper).toSimpleDTOs(argThat(comments -> comments.size() == 2));
    }
}
