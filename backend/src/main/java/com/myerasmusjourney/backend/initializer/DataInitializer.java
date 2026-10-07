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

    private void initiateUsers() {
        UserFormDTO userFormDTO = new UserFormDTO("test@email.com", "test", "testUser", null, null, "password", "password");
        UserFormDTO userFormDTO1 = new UserFormDTO("exampleuser1@email.com", "Daniel", "Daniel Grimm", "Berlin", "Germany", "password", "password");
        UserFormDTO userFormDTO2 = new UserFormDTO("exampleuser2@email.com", "Maria", "Maria Garcia", "Madrid", "Spain", "password", "password");
        UserFormDTO userFormDTO3 = new UserFormDTO("exampleuser3@email.com", "Max", "Max Helmut", "Hamburg", "Germany", "password", "password");
        UserFormDTO userFormDTO4 = new UserFormDTO("exampleuser4@email.com", "Sofia", "Sofia Martin", "Madrid", "Spain", "password", "password");
        UserFormDTO userFormDTO5 = new UserFormDTO("exampleuser5@email.com", "Lukas", "Lukas Schneider", "Munich", "Germany", "password", "password");
        UserFormDTO userFormDTO6 = new UserFormDTO("exampleuser6@email.com", "Emma", "Emma Wilson", "London", "United Kingdom", "password", "password");
        UserFormDTO userFormDTO7 = new UserFormDTO("exampleuser7@email.com", "Joao", "Joao Silva", "Porto", "Portugal", "password", "password");
        UserFormDTO userFormDTO8 = new UserFormDTO("exampleuser8@email.com", "Anna", "Anna Novak", "Prague", "Czech Republic", "password", "password");
        UserFormDTO userFormDTO9 = new UserFormDTO("exampleuser9@email.com", "Clara", "Clara Rossi", "Milan", "Italy", "password", "password");
        UserFormDTO userFormDTO10 = new UserFormDTO("exampleuser10@email.com", "Thomas", "Thomas Müller", "Frankfurt", "Germany", "password", "password");
        UserFormDTO userFormDTO11 = new UserFormDTO("exampleuser11@email.com", "Elena", "Elena García", "Valencia", "Spain", "password", "password");
        UserFormDTO userFormDTO12 = new UserFormDTO("exampleuser12@email.com", "James", "James Brown", "Manchester", "United Kingdom", "password", "password");
        UserFormDTO userFormDTO13 = new UserFormDTO("exampleuser13@email.com", "Camille", "Camille Dubois", "Lyon", "France", "password", "password");
        UserFormDTO userFormDTO14 = new UserFormDTO("exampleuser14@email.com", "Marco", "Marco Bianchi", "Rome", "Italy", "password", "password");
        UserFormDTO userFormDTO15 = new UserFormDTO("exampleuser15@email.com", "Laura", "Laura Pérez", "Seville", "Spain", "password", "password");
        UserFormDTO userFormDTO16 = new UserFormDTO("exampleuser16@email.com", "Oliver", "Oliver Smith", "Bristol", "United Kingdom", "password", "password");

        userService.createUser(userFormDTO);
        userService.createUser(userFormDTO1);
        userService.createUser(userFormDTO2);
        userService.createUser(userFormDTO3);
        userService.createUser(userFormDTO4);
        userService.createUser(userFormDTO5);
        userService.createUser(userFormDTO6);
        userService.createUser(userFormDTO7);
        userService.createUser(userFormDTO8);
        userService.createUser(userFormDTO9);
        userService.createUser(userFormDTO10);
        userService.createUser(userFormDTO11);
        userService.createUser(userFormDTO12);
        userService.createUser(userFormDTO13);
        userService.createUser(userFormDTO14);
        userService.createUser(userFormDTO15);
        userService.createUser(userFormDTO16);
    }

    private void initiateCities(){
        CityFormDTO cityFormDTO1 = new CityFormDTO("Copenhagen", "Capital of Denmark", "Denmark");
        CityFormDTO cityFormDTO2 = new CityFormDTO("Paris", "Capital of France", "France");
        CityFormDTO cityFormDTO3= new CityFormDTO("Rome", "Capital of Italy", "Italy");
        CityFormDTO cityFormDTO4 = new CityFormDTO("Barcelona", "A lively Mediterranean city with beaches, culture and a large international student community.", "Spain");
        CityFormDTO cityFormDTO5 = new CityFormDTO("Berlin", "A diverse European capital known for its history, culture and international atmosphere.", "Germany");
        CityFormDTO cityFormDTO6 = new CityFormDTO("Amsterdam", "A student-friendly city known for its canals, cycling culture and international community.", "Netherlands");
        CityFormDTO cityFormDTO7 = new CityFormDTO("Lisbon", "A sunny European capital with a growing international student community and affordable neighbourhoods.", "Portugal");
        CityFormDTO cityFormDTO8 = new CityFormDTO("Prague", "A historic Central European city with a large student population and relatively affordable living costs.", "Czech Republic");

        cityService.addCity(cityFormDTO1);
        cityService.addCity(cityFormDTO2);
        cityService.addCity(cityFormDTO3);
        cityService.addCity(cityFormDTO4);
        cityService.addCity(cityFormDTO5);
        cityService.addCity(cityFormDTO6);
        cityService.addCity(cityFormDTO7);
        cityService.addCity(cityFormDTO8);
    }

    private void initiateExperiences() {
        ExperienceFormDTO experienceFormDTO1 = new ExperienceFormDTO(7.8F, "Finding accommodation in Paris", "Finding a room in Paris was challenging at first, but university groups and student housing websites helped me find a good place close to campus.", LocalDate.now().minusDays(13), List.of("Accommodation", "Studies"), 2L);
        ExperienceFormDTO experienceFormDTO2 = new ExperienceFormDTO(8.8F, "Exploring Paris with Erasmus friends", "Exploring different neighbourhoods with other Erasmus students has been one of the best parts of my Erasmus experience so far.", LocalDate.now().minusDays(3), List.of("Culture", "Social_Events"), 2L);

        ExperienceFormDTO experienceFormDTO3 = new ExperienceFormDTO(8.0F, "Getting used to university in Paris", "The university environment was different from what I was used to, but the international atmosphere made it easy to adapt.", LocalDate.now().minusDays(11), List.of("Studies", "Personal_Experience"), 2L);
        ExperienceFormDTO experienceFormDTO4 = new ExperienceFormDTO(8.7F, "Student life in Paris", "There are many activities for international students and it has been very easy to meet people from different countries.", LocalDate.now().minusDays(1), List.of("Social_Events", "Personal_Experience"), 2L);


        ExperienceFormDTO experienceFormDTO5 = new ExperienceFormDTO(8.8F, "Discovering Rome on foot", "Walking around Rome after classes became one of my favourite parts of the Erasmus. There was always another square, monument or neighbourhood to discover.", LocalDate.now().minusDays(13), List.of("Culture", "Personal_Experience"), 3L);
        ExperienceFormDTO experienceFormDTO6 = new ExperienceFormDTO(8.4F, "Italian food beyond the tourist areas", "Trying small restaurants away from the main tourist attractions was one of the best decisions I made. I discovered several inexpensive places popular with students.", LocalDate.now().minusDays(4), List.of("Gastronomy", "Culture"), 3L);

        ExperienceFormDTO experienceFormDTO7 = new ExperienceFormDTO(8.9F, "Finding an apartment in Rome", "The apartment search took some time because many offers were either expensive or far from the university. Sharing a flat with other students worked well in the end.", LocalDate.now().minusDays(10), List.of("Accommodation", "Personal_Experience"), 3L);
        ExperienceFormDTO experienceFormDTO8 = new ExperienceFormDTO(8.2F, "A weekend trip from Rome", "Living in Rome made it easy to organise weekend trips to other parts of Italy. Travelling with Erasmus friends was a great way to meet people from different countries.", LocalDate.now().minusDays(2), List.of("Social_Events", "Transportation", "Personal_Experience"), 3L);


        ExperienceFormDTO experienceFormDTO9 = new ExperienceFormDTO(8.5F, "Getting around Copenhagen", "Cycling became my main way of getting around Copenhagen. The city is very convenient for students who want to use a bicycle for everyday journeys.", LocalDate.now().minusDays(12), List.of("Transportation", "Personal_Experience"), 1L);
        ExperienceFormDTO experienceFormDTO10 = new ExperienceFormDTO(8.7F, "The international student community", "The Erasmus community in Copenhagen was very welcoming. University events and student organisations made it easy to meet people during the first weeks.", LocalDate.now().minusDays(5), List.of("Social_Events", "Culture"), 1L);

        ExperienceFormDTO experienceFormDTO11 = new ExperienceFormDTO(7.9F, "Preparing the Erasmus documents", "There was quite a lot of paperwork before leaving for Denmark. Creating a checklist with deadlines helped me avoid forgetting important documents.", LocalDate.now().minusDays(9), List.of("Documentation", "Studies"), 1L);
        ExperienceFormDTO experienceFormDTO12 = new ExperienceFormDTO(8.4F, "Studying in Copenhagen", "The teaching style was more focused on participation and group work than I expected. It took some time to adapt, but I ended up enjoying the experience.", LocalDate.now().minusDays(2), List.of("Studies", "Personal_Experience"), 1L);


        ExperienceFormDTO experienceFormDTO13 = new ExperienceFormDTO(7.2F, "Finding student accommodation in Barcelona", "Finding accommodation took some effort, but student groups and university recommendations helped me find a shared apartment near campus.", LocalDate.now().minusDays(13), List.of("Accommodation", "Studies"), 4L);
        ExperienceFormDTO experienceFormDTO14 = new ExperienceFormDTO(9.2F, "Exploring Barcelona with Erasmus friends", "Exploring different neighbourhoods with other Erasmus students has been one of the highlights of my stay so far.", LocalDate.now().minusDays(6), List.of("Culture", "Social_Events"), 4L);

        ExperienceFormDTO experienceFormDTO15 = new ExperienceFormDTO(7.5F, "Getting around Barcelona", "The public transport network made it easy to move around the city, although I ended up walking most of the time.", LocalDate.now().minusDays(10), List.of("Transportation", "Personal_Experience"), 4L);
        ExperienceFormDTO experienceFormDTO16 = new ExperienceFormDTO(9.0F, "Student life in Barcelona", "There are many activities for international students and it has been very easy to meet people from different countries.", LocalDate.now().minusDays(4), List.of("Social_Events", "Personal_Experience"), 4L);

        ExperienceFormDTO experienceFormDTO17 = new ExperienceFormDTO(7.8F, "University life in Barcelona", "The international environment at university made it easy to meet students from different countries and adapt to the new academic environment.", LocalDate.now().minusDays(12), List.of("Studies", "Social_Events"), 4L);
        ExperienceFormDTO experienceFormDTO18 = new ExperienceFormDTO(9.4F, "Weekend at the beach", "Having beaches so close to the city makes weekends very different from my usual university routine.", LocalDate.now().minusDays(1), List.of("Personal_Experience", "Culture"), 4L);


        ExperienceFormDTO experienceFormDTO19 = new ExperienceFormDTO(7.8F, "Finding accommodation in Berlin", "The housing market was competitive, but university groups and shared apartments were useful for finding a room.", LocalDate.now().minusDays(13), List.of("Accommodation", "Documentation"), 5L);
        ExperienceFormDTO experienceFormDTO20 = new ExperienceFormDTO(8.6F, "Exploring Berlin neighbourhoods", "Each neighbourhood has a very different atmosphere, which makes exploring the city especially interesting.", LocalDate.now().minusDays(6), List.of("Culture", "Personal_Experience"), 5L);

        ExperienceFormDTO experienceFormDTO21 = new ExperienceFormDTO(8.0F, "Public transport in Berlin", "The public transport network is extensive and useful for getting around the city and visiting different neighbourhoods.", LocalDate.now().minusDays(10), List.of("Transportation", "Personal_Experience"), 5L);
        ExperienceFormDTO experienceFormDTO22 = new ExperienceFormDTO(8.8F, "Museums in Berlin", "The number of museums and historical places to visit is impressive, especially for students interested in European history.", LocalDate.now().minusDays(3), List.of("Culture", "Personal_Experience"), 5L);

        ExperienceFormDTO experienceFormDTO23 = new ExperienceFormDTO(8.1F, "Student life in Berlin", "There are many student organisations and international events, although the city can feel quite large at first.", LocalDate.now().minusDays(11), List.of("Social_Events", "Studies"), 5L);
        ExperienceFormDTO experienceFormDTO24 = new ExperienceFormDTO(8.7F, "Erasmus events in Berlin", "International student events have made it much easier to meet people and discover the city.", LocalDate.now().minusDays(2), List.of("Social_Events", "Culture"), 5L);


        ExperienceFormDTO experienceFormDTO25 = new ExperienceFormDTO(8.0F, "Cycling in Amsterdam", "Cycling is one of the easiest ways to get around Amsterdam and quickly became part of my daily routine.", LocalDate.now().minusDays(12), List.of("Transportation", "Personal_Experience"), 6L);
        ExperienceFormDTO experienceFormDTO26 = new ExperienceFormDTO(8.6F, "Exploring Amsterdam by bike", "Cycling around the canals and different neighbourhoods has been one of my favourite parts of living here.", LocalDate.now().minusDays(5), List.of("Transportation", "Culture"), 6L);

        ExperienceFormDTO experienceFormDTO27 = new ExperienceFormDTO(8.2F, "Finding student accommodation", "Accommodation was expensive, but sharing a flat with other students made it more manageable.", LocalDate.now().minusDays(9), List.of("Accommodation"), 6L);
        ExperienceFormDTO experienceFormDTO28 = new ExperienceFormDTO(8.7F, "Student events in Amsterdam", "There are many events organised for international students, making it easy to meet people.", LocalDate.now().minusDays(2), List.of("Social_Events"), 6L);


        ExperienceFormDTO experienceFormDTO29 = new ExperienceFormDTO(7.4F, "Finding accommodation in Lisbon", "The accommodation search took some time, but student groups were useful for finding shared apartments.", LocalDate.now().minusDays(13), List.of("Accommodation"), 7L);
        ExperienceFormDTO experienceFormDTO30 = new ExperienceFormDTO(8.8F, "Student life in Lisbon", "Lisbon has a relaxed atmosphere and the international student community is very welcoming.", LocalDate.now().minusDays(4), List.of("Personal_Experience", "Social_Events"), 7L);

        ExperienceFormDTO experienceFormDTO31 = new ExperienceFormDTO(8.6F, "Exploring Lisbon", "The city is full of viewpoints, historic streets and affordable places to eat.", LocalDate.now().minusDays(7), List.of("Culture", "Gastronomy"), 7L);
        ExperienceFormDTO experienceFormDTO32 = new ExperienceFormDTO(9.0F, "Weekend activities in Lisbon", "There are many places to visit and the city's relaxed atmosphere makes it easy to enjoy weekends.", LocalDate.now().minusDays(1), List.of("Culture", "Personal_Experience"), 7L);


        ExperienceFormDTO experienceFormDTO33 = new ExperienceFormDTO(7.9F, "Living in Prague as an Erasmus student", "Prague is relatively affordable compared with other European capitals and there are many students in the city.", LocalDate.now().minusDays(13), List.of("Personal_Experience", "Studies"), 8L);
        ExperienceFormDTO experienceFormDTO34 = new ExperienceFormDTO(8.5F, "Exploring Prague", "The historic centre is beautiful and there are many interesting places to discover outside the main tourist areas.", LocalDate.now().minusDays(6), List.of("Culture", "Personal_Experience"), 8L);

        ExperienceFormDTO experienceFormDTO35 = new ExperienceFormDTO(8.1F, "Public transport in Prague", "The public transport system is convenient and makes it easy to get to university and explore the city.", LocalDate.now().minusDays(10), List.of("Transportation"), 8L);
        ExperienceFormDTO experienceFormDTO36 = new ExperienceFormDTO(8.7F, "Erasmus events in Prague", "International events organised by students are a great way to meet people and explore the city together.", LocalDate.now().minusDays(3), List.of("Social_Events", "Culture"), 8L);


        Authentication authentication = new UsernamePasswordAuthenticationToken("exampleuser1@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceService.createExperience(experienceFormDTO1);
        experienceService.createExperience(experienceFormDTO2);


        authentication = new UsernamePasswordAuthenticationToken("exampleuser2@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceService.createExperience(experienceFormDTO5);
        experienceService.createExperience(experienceFormDTO6);


        authentication = new UsernamePasswordAuthenticationToken("exampleuser3@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceService.createExperience(experienceFormDTO9);
        experienceService.createExperience(experienceFormDTO10);


        authentication = new UsernamePasswordAuthenticationToken("exampleuser4@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceService.createExperience(experienceFormDTO19);
        experienceService.createExperience(experienceFormDTO20);


        authentication = new UsernamePasswordAuthenticationToken("exampleuser5@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceService.createExperience(experienceFormDTO13);
        experienceService.createExperience(experienceFormDTO14);


        authentication = new UsernamePasswordAuthenticationToken("exampleuser6@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceService.createExperience(experienceFormDTO25);
        experienceService.createExperience(experienceFormDTO26);


        authentication = new UsernamePasswordAuthenticationToken("exampleuser7@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceService.createExperience(experienceFormDTO35);
        experienceService.createExperience(experienceFormDTO36);


        authentication = new UsernamePasswordAuthenticationToken("exampleuser8@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceService.createExperience(experienceFormDTO31);
        experienceService.createExperience(experienceFormDTO32);


        authentication = new UsernamePasswordAuthenticationToken("exampleuser9@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceService.createExperience(experienceFormDTO15);
        experienceService.createExperience(experienceFormDTO16);


        authentication = new UsernamePasswordAuthenticationToken("exampleuser10@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceService.createExperience(experienceFormDTO17);
        experienceService.createExperience(experienceFormDTO18);


        authentication = new UsernamePasswordAuthenticationToken("exampleuser11@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceService.createExperience(experienceFormDTO21);
        experienceService.createExperience(experienceFormDTO22);


        authentication = new UsernamePasswordAuthenticationToken("exampleuser12@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceService.createExperience(experienceFormDTO23);
        experienceService.createExperience(experienceFormDTO24);


        authentication = new UsernamePasswordAuthenticationToken("exampleuser13@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceService.createExperience(experienceFormDTO27);
        experienceService.createExperience(experienceFormDTO28);


        authentication = new UsernamePasswordAuthenticationToken("exampleuser14@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceService.createExperience(experienceFormDTO7);
        experienceService.createExperience(experienceFormDTO8);


        authentication = new UsernamePasswordAuthenticationToken("exampleuser15@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceService.createExperience(experienceFormDTO3);
        experienceService.createExperience(experienceFormDTO4);


        authentication = new UsernamePasswordAuthenticationToken("exampleuser16@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceService.createExperience(experienceFormDTO11);
        experienceService.createExperience(experienceFormDTO12);
    }

    private void initiateComments(){
        CommentFormDTO commentFormDTO = new CommentFormDTO("My opinion or point of view regarding the experience");

        Authentication authentication = new UsernamePasswordAuthenticationToken("exampleuser3@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        commentService.postComment(1L, commentFormDTO);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser2@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        commentService.postComment(3L, commentFormDTO);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser1@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        commentService.postComment(2L, commentFormDTO);

    }

    @PostConstruct
    @Transactional
    public void init(){

        commentService.emptyComments();
        experienceService.emptyExperiences();
        cityService.emptyCities();
        userService.emptyUsers();

        initiateUsers();
        initiateCities();
        initiateExperiences();
        initiateComments();
    }
}
