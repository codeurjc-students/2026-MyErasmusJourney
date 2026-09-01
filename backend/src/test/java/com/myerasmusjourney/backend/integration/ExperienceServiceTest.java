package com.myerasmusjourney.backend.integration;

import com.myerasmusjourney.backend.TestDataBase;
import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.*;
import com.myerasmusjourney.backend.enumeration.Category;
import com.myerasmusjourney.backend.mapper.ExperienceMapper;
import com.myerasmusjourney.backend.repository.CityRepository;
import com.myerasmusjourney.backend.repository.ExperienceRepository;
import com.myerasmusjourney.backend.repository.UserRepository;
import com.myerasmusjourney.backend.service.CommentService;
import com.myerasmusjourney.backend.service.ExperienceService;
import com.myerasmusjourney.backend.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Tag("integration")
public class ExperienceServiceTest extends TestDataBase {

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ExperienceMapper experienceMapper;

    private List<ExperienceSimpleDTO> expected;

    @BeforeEach
    void setup(){

        if (experienceRepository.count() > 0){
            experienceRepository.deleteAll();
        }

        List<User> users = userRepository.findAll();

        User author1 = users.getFirst();
        User author2 = users.get(1);

        List<City> cities = cityRepository.findAll();

        City city1 = cities.getFirst();
        City city2 = cities.get(1);

        List<Experience> experiences = List.of(
                new Experience(
                        "Experiencia 1", "Descripcion 1", 9F, null,
                        List.of(Category.Studies.name(), Category.Documentation.name()),
                        city1, author1
                ),
                new Experience(
                        "Experiencia 2", "Descripcion 2", 8.67F, null,
                        List.of(Category.Personal_Experience.name(), Category.Transportation.name()),
                        city2, author1
                ),
                new Experience(
                        "Experiencia 3", "Descripcion 3", 5.4F, null,
                        List.of(Category.Social_Events.name(), Category.Culture.name()),
                        city1, author2
                ),
                new Experience(
                        "Experiencia 4", "Descripcion 4", 0.9F, null,
                        List.of(Category.Accommodation.name(), Category.Documentation.name()),
                        city2, author2
                ),
                new Experience(
                        "Experiencia 5", "Descripcion 5", 7.5F, null,
                        List.of(Category.Studies.name()),
                        city1, author1
                ),
                new Experience(
                        "Experiencia 6", "Descripcion 6", 6.8F, null,
                        List.of(Category.Culture.name()),
                        city2, author1
                ),
                new Experience(
                        "Experiencia 7", "Descripcion 7", 9.2F, null,
                        List.of(Category.Gastronomy.name()),
                        city1, author2
                ),
                new Experience(
                        "Experiencia 8", "Descripcion 8", 4.3F, null,
                        List.of(Category.Accommodation.name()),
                        city2, author2
                ),
                new Experience(
                        "Experiencia 9", "Descripcion 9", 8.1F, null,
                        List.of(Category.Social_Events.name()),
                        city1, author1
                ),
                new Experience(
                        "Experiencia 10", "Descripcion 10", 7.9F, null,
                        List.of(Category.Transportation.name()),
                        city2, author1
                ),
                new Experience(
                        "Experiencia 11", "Descripcion 11", 5.7F, null,
                        List.of(Category.Personal_Experience.name()),
                        city1, author2
                ),
                new Experience(
                        "Experiencia 12", "Descripcion 12", 3.6F, null,
                        List.of(Category.Documentation.name()),
                        city2, author2
                )
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

        Pageable pageable = PageRequest.of(0, 10);

        Page<ExperienceSimpleDTO> result =
                experienceService.getAllExperiences(pageable);

        assertEquals(expected.size(), result.getTotalElements());
        assertEquals(10, result.getNumberOfElements());

        Long id = result.getContent().getFirst().id();

        for(int i = 0; i < 10; i++){
            ExperienceSimpleDTO exp = expected.get(i);
            ExperienceSimpleDTO res = result.getContent().get(i);

            assertEquals(exp, res);
            assertEquals(id, res.id());

            id++;
        }
    }

    @Test
    void testGetAllDynamic(){

        Pageable pageable = PageRequest.of(0, 10);

        Page<ExperienceSimpleDTO> result =
                experienceService.getAllExperiences(pageable);

        assertEquals(expected.size(), result.getTotalElements());

        Experience experience = new Experience(
                "Experience 5",
                "Descripcion 5",
                2.4F,
                null,
                List.of(Category.Personal_Experience.name()),
                null,
                null
        );

        Experience savedExperience = experienceRepository.save(experience);

        ExperienceSimpleDTO savedDTO = new ExperienceSimpleDTO(
                savedExperience.getId(),
                savedExperience.getDate(),
                savedExperience.getRating(),
                savedExperience.getTitle(),
                savedExperience.getDescription(),
                savedExperience.getCategories(),
                "Berlin",
                "Germany",
                "user2"
        );

        expected.add(savedDTO);

        result = experienceService.getAllExperiences(pageable);

        assertEquals(expected.size(), result.getTotalElements());
        assertEquals(10, result.getNumberOfElements());

        Long id = result.getContent().getFirst().id();

        for(int i = 0; i < 10; i++){
            ExperienceSimpleDTO exp = expected.get(i);
            ExperienceSimpleDTO res = result.getContent().get(i);

            assertEquals(exp.id(), res.id());
            assertEquals(exp.description(), res.description());
            assertEquals(exp.title(), res.title());
            assertEquals(exp.date(), res.date());
            assertEquals(exp.rating(), res.rating());
            assertEquals(exp.categories().size(), res.categories().size());
            assertEquals(id, res.id());
            assertEquals(exp.authorName(), res.authorName());
            assertEquals(exp.cityName(), res.cityName());
            assertEquals(exp.country(), res.country());

            id++;
        }
    }

    @Test
    void testGetAllPagination() {

        Pageable pageable = PageRequest.of(0, 5);

        Page<ExperienceSimpleDTO> result =
                experienceService.getAllExperiences(pageable);

        assertEquals(12, result.getTotalElements());
        assertEquals(5, result.getNumberOfElements());
        assertEquals(3, result.getTotalPages());
        assertEquals(0, result.getNumber());

        for (int i = 0; i < 5; i++) {
            assertEquals(expected.get(i), result.getContent().get(i));
        }

        pageable = PageRequest.of(1, 5);

        result = experienceService.getAllExperiences(pageable);

        assertEquals(12, result.getTotalElements());
        assertEquals(5, result.getNumberOfElements());
        assertEquals(3, result.getTotalPages());
        assertEquals(1, result.getNumber());

        for (int i = 0; i < 5; i++) {
            assertEquals(expected.get(i + 5), result.getContent().get(i));
        }

        pageable = PageRequest.of(2, 5);

        result = experienceService.getAllExperiences(pageable);

        assertEquals(12, result.getTotalElements());
        assertEquals(2, result.getNumberOfElements());
        assertEquals(3, result.getTotalPages());
        assertEquals(2, result.getNumber());

        assertEquals(expected.get(10), result.getContent().get(0));
        assertEquals(expected.get(11), result.getContent().get(1));
    }

    @Test
    void testGetAllEmpty() {

        experienceRepository.deleteAll();

        Pageable pageable = PageRequest.of(0, 10);

        Page<ExperienceSimpleDTO> result =
                experienceService.getAllExperiences(pageable);

        assertTrue(result.isEmpty());
    }

    @Test
    void testCreateExperience(){

        Authentication authentication = new UsernamePasswordAuthenticationToken("test@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        List<City> cities = cityRepository.findAll();

        ExperienceFormDTO formDTO = new ExperienceFormDTO(
                5.0f,
                "Erasmus party",
                "Great Erasmus party",
                LocalDate.of(2022, 1, 13),
                List.of("Social_Events", "Gastronomy"),
                cities.getFirst().getId()
        );

        City city = cities.getFirst();
        User user = userService.getLoggedUser();

        Experience savedExperience = new Experience(
                formDTO.title(),
                formDTO.description(),
                formDTO.rating(),
                null,
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
                userDTO,
                List.of()
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

    @Test
    @Transactional
    void testGetExperienceById() {

        ExperienceDTO expected = experienceMapper.toDTO(experienceRepository.findAll().getFirst());

        ExperienceDTO result = experienceService.getExperienceById(expected.id());

        Assert.assertEquals(expected, result);
    }

    @Test
    void testGetExperienceByIdNotFound() {
        Long id = 0L;

        assertThrows(NoSuchElementException.class, () -> experienceService.getExperienceById(id));
    }

    @Test
    void testGetComments(){
        Authentication authentication = new UsernamePasswordAuthenticationToken("test@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Experience experience = experienceRepository.findAll().getFirst();

        Collection<CommentSimpleDTO> result = experienceService.getComments(experience.getId());

        assertTrue(result.isEmpty());

        CommentDTO commentDTO = commentService.postComment(experience.getId(), new CommentFormDTO("New comment"));

        CommentSimpleDTO expected = new CommentSimpleDTO(commentDTO.id(), commentDTO.date(), commentDTO.description(), commentDTO.author().displayName(), experience.getId());

        result = experienceService.getComments(experience.getId());

        assertEquals(1, result.size());
        assertTrue(result.contains(expected));
    }

    @Test
    @Transactional
    void testDeleteExperienceByIdSuccess(){

        Authentication authentication = new UsernamePasswordAuthenticationToken("exampleuser1@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userService.getLoggedUser();

        ExperienceDTO expected = experienceMapper.toDTO(user.getExperiences().getFirst());

        ExperienceDTO result = experienceService.deleteExperienceById(expected.id());

        assertEquals(expected, result);
    }

    @Test
    @Transactional
    void testDeleteExperienceByIdFail(){

        Authentication authentication = new UsernamePasswordAuthenticationToken("test@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Experience expected = experienceRepository.findAll().getFirst();

        ExperienceDTO result = experienceService.deleteExperienceById(expected.getId());

        assertNull(result);
    }

    @Test
    @Transactional
    void testDeleteExperienceByAdmin(){
        Authentication authentication = new UsernamePasswordAuthenticationToken("testadmin@email.com",null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Experience experience = experienceRepository.findAll().getFirst();
        ExperienceDTO expected = experienceMapper.toDTO(experience);

        ExperienceDTO result = experienceService.deleteExperienceById(experience.getId());

        assertEquals(expected, result);
    }
}
