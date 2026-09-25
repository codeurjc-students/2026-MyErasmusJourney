package com.myerasmusjourney.backend.initializer;

import com.myerasmusjourney.backend.dto.*;
import com.myerasmusjourney.backend.service.CityService;
import com.myerasmusjourney.backend.service.CommentService;
import com.myerasmusjourney.backend.service.ExperienceService;
import com.myerasmusjourney.backend.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Profile("test")
@Component
public class DataInitializer {

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private CommentService commentService;

    @PostConstruct
    @Transactional
    public void init(){

        commentService.emptyComments();
        experienceService.emptyExperiences();
        cityService.emptyCities();
        userService.emptyUsers();

        UserFormDTO userFormDTO = new UserFormDTO("test@email.com", "test", "testUser", null, null, "password", "password");
        UserFormDTO userFormDTO1 = new UserFormDTO("exampleuser1@email.com", "Daniel", "Daniel Grimm", "Paris", "France", "password", "password");
        UserFormDTO userFormDTO2= new UserFormDTO("exampleuser2@email.com", "Maria", "Maria Garcia", "Rome", "Italy", "password", "password");
        UserFormDTO userFormDTO3= new UserFormDTO("exampleuser3@email.com", "Max", "Max Helmut", "Copenhagen", "Denmark", "password", "password");

        userService.createUser(userFormDTO);
        userService.createUser(userFormDTO1);
        userService.createUser(userFormDTO2);
        userService.createUser(userFormDTO3);

        CityFormDTO cityFormDTO1 = new CityFormDTO("Copenhagen", "Capital of Denmark", "Denmark");
        CityFormDTO cityFormDTO2 = new CityFormDTO("Paris", "Capital of France", "France");
        CityFormDTO cityFormDTO3= new CityFormDTO("Rome", "Capital of Italy", "Italy");

        cityService.addCity(cityFormDTO1);
        cityService.addCity(cityFormDTO2);
        cityService.addCity(cityFormDTO3);

        ExperienceFormDTO experienceFormDTO1 = new ExperienceFormDTO(
                8.7F,
                "Finding accommodation in Paris",
                "Finding a room in Paris was challenging at first, but university groups and student housing websites helped me find a good place close to campus.",
                LocalDate.now().minusMonths(5),
                List.of("Accommodation", "Studies"),
                2L
        );

        ExperienceFormDTO experienceFormDTO2 = new ExperienceFormDTO(
                9.2F,
                "My first weeks in Paris",
                "The first weeks were full of new experiences. Getting used to the city, meeting international students and exploring the university made the beginning unforgettable.",
                LocalDate.now().minusMonths(4),
                List.of("Personal_Experience", "Culture", "Social_Events"),
                2L
        );

        ExperienceFormDTO experienceFormDTO3 = new ExperienceFormDTO(
                7.8F,
                "University life in Paris",
                "Classes were different from what I was used to, especially the amount of independent work. The international environment made studying much more enjoyable.",
                LocalDate.now().minusMonths(3),
                List.of("Studies", "Personal_Experience"),
                2L
        );

        ExperienceFormDTO experienceFormDTO4 = new ExperienceFormDTO(
                8.9F,
                "Dealing with Erasmus paperwork",
                "The university required several documents before the semester started. Keeping digital copies of everything made the administrative process much easier.",
                LocalDate.now().minusMonths(2),
                List.of("Documentation", "Studies"),
                2L
        );

        ExperienceFormDTO experienceFormDTO5 = new ExperienceFormDTO(
                9.5F,
                "Discovering Rome on foot",
                "Walking around Rome after classes became one of my favourite parts of the Erasmus. There was always another square, monument or neighbourhood to discover.",
                LocalDate.now().minusMonths(6),
                List.of("Culture", "Personal_Experience"),
                3L
        );

        ExperienceFormDTO experienceFormDTO6 = new ExperienceFormDTO(
                8.3F,
                "Italian food beyond the tourist areas",
                "Trying small restaurants away from the main tourist attractions was one of the best decisions I made. I discovered several inexpensive places popular with students.",
                LocalDate.now().minusMonths(5),
                List.of("Gastronomy", "Culture"),
                3L
        );

        ExperienceFormDTO experienceFormDTO7 = new ExperienceFormDTO(
                7.4F,
                "Finding an apartment in Rome",
                "The apartment search took some time because many offers were either expensive or far from the university. Sharing a flat with other students worked well in the end.",
                LocalDate.now().minusMonths(4),
                List.of("Accommodation", "Personal_Experience"),
                3L
        );

        ExperienceFormDTO experienceFormDTO8 = new ExperienceFormDTO(
                9.0F,
                "A weekend trip from Rome",
                "Living in Rome made it easy to organise weekend trips to other parts of Italy. Travelling with Erasmus friends was a great way to meet people from different countries.",
                LocalDate.now().minusMonths(3),
                List.of("Social_Events", "Transportation", "Personal_Experience"),
                3L
        );

        ExperienceFormDTO experienceFormDTO9 = new ExperienceFormDTO(
                8.6F,
                "Getting around Copenhagen",
                "Cycling became my main way of getting around Copenhagen. The city is very convenient for students who want to use a bicycle for everyday journeys.",
                LocalDate.now().minusMonths(7),
                List.of("Transportation", "Personal_Experience"),
                1L
        );

        ExperienceFormDTO experienceFormDTO10 = new ExperienceFormDTO(
                9.1F,
                "The international student community",
                "The Erasmus community in Copenhagen was very welcoming. University events and student organisations made it easy to meet people during the first weeks.",
                LocalDate.now().minusMonths(6),
                List.of("Social_Events", "Culture"),
                1L
        );

        ExperienceFormDTO experienceFormDTO11 = new ExperienceFormDTO(
                6.9F,
                "Preparing the Erasmus documents",
                "There was quite a lot of paperwork before leaving for Denmark. Creating a checklist with deadlines helped me avoid forgetting important documents.",
                LocalDate.now().minusMonths(5),
                List.of("Documentation", "Studies"),
                1L
        );

        ExperienceFormDTO experienceFormDTO12 = new ExperienceFormDTO(
                8.0F,
                "Studying in Copenhagen",
                "The teaching style was more focused on participation and group work than I expected. It took some time to adapt, but I ended up enjoying the experience.",
                LocalDate.now().minusMonths(4),
                List.of("Studies", "Personal_Experience"),
                1L
        );

        CommentFormDTO commentFormDTO = new CommentFormDTO("My opinion or point of view regarding the experience");

        Authentication authentication = new UsernamePasswordAuthenticationToken("exampleuser1@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceService.createExperience(experienceFormDTO1);
        experienceService.createExperience(experienceFormDTO9);
        experienceService.createExperience(experienceFormDTO10);
        experienceService.createExperience(experienceFormDTO11);


        authentication = new UsernamePasswordAuthenticationToken("exampleuser2@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceService.createExperience(experienceFormDTO2);
        experienceService.createExperience(experienceFormDTO4);
        experienceService.createExperience(experienceFormDTO5);
        experienceService.createExperience(experienceFormDTO12);


        authentication = new UsernamePasswordAuthenticationToken("exampleuser3@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceService.createExperience(experienceFormDTO3);
        experienceService.createExperience(experienceFormDTO6);
        experienceService.createExperience(experienceFormDTO7);
        experienceService.createExperience(experienceFormDTO8);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser3@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        commentService.postComment(1L, commentFormDTO);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser2@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        commentService.postComment(3L, commentFormDTO);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser1@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        commentService.postComment(2L, commentFormDTO);

    }
}
