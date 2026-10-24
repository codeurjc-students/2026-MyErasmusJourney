package com.myerasmusjourney.backend.initializer;

import com.myerasmusjourney.backend.dto.*;
import com.myerasmusjourney.backend.service.*;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Profile("test")
@Component
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MultimediaService multimediaService;

    private final HashMap<Long, Long> citiesId = new HashMap<>();

    private final HashMap<Long, Long> usersId = new HashMap<>();

    private final HashMap<Long, Long> experiencesId = new HashMap<>();


    private void initiateUsers() {
        UserFormDTO userFormDTO = new UserFormDTO("test@email.com", "test", "testUser", null, null, "password", "password");
        UserFormDTO userFormDTO1 = new UserFormDTO("exampleuser1@email.com", "Daniel", "Daniel Grimm", "Berlin", "Germany", "password", "password");
        UserFormDTO userFormDTO2 = new UserFormDTO("exampleuser2@email.com", "Maria", "Maria Garcia", "Madrid", "Spain", "password", "password");
        UserFormDTO userFormDTO3 = new UserFormDTO("exampleuser3@email.com", "Max", "Max Helmut", "Hamburg", "Germany", "password", "password");
        UserFormDTO userFormDTO4 = new UserFormDTO("exampleuser4@email.com", "Sofia", "Sofia Martin", "Madrid", "Spain", "password", "password");
        UserFormDTO userFormDTO5 = new UserFormDTO("exampleuser5@email.com", "Lukas", "Lukas Schneider", "Munich", "Germany", "password", "password");
        UserFormDTO userFormDTO6 = new UserFormDTO("exampleuser6@email.com", "Emma", "Emma Wilson", "London", "United Kingdom", "password", "password");
        UserFormDTO userFormDTO7 = new UserFormDTO("exampleuser7@email.com", "Joao", "Joao Silva", "Prague", "Czech Republic", "password", "password");
        UserFormDTO userFormDTO8 = new UserFormDTO("exampleuser8@email.com", "Anna", "Anna Novak", "Lisbon", "Portugal", "password", "password");
        UserFormDTO userFormDTO9 = new UserFormDTO("exampleuser9@email.com", "Clara", "Clara Rossi", "Milan", "Italy", "password", "password");
        UserFormDTO userFormDTO10 = new UserFormDTO("exampleuser10@email.com", "Thomas", "Thomas Müller", "Frankfurt", "Germany", "password", "password");
        UserFormDTO userFormDTO11 = new UserFormDTO("exampleuser11@email.com", "Elena", "Elena García", "Valencia", "Spain", "password", "password");
        UserFormDTO userFormDTO12 = new UserFormDTO("exampleuser12@email.com", "James", "James Brown", "Manchester", "United Kingdom", "password", "password");
        UserFormDTO userFormDTO13 = new UserFormDTO("exampleuser13@email.com", "Camille", "Camille Dubois", "Lyon", "France", "password", "password");
        UserFormDTO userFormDTO14 = new UserFormDTO("exampleuser14@email.com", "Marco", "Marco Bianchi", "Rome", "Italy", "password", "password");
        UserFormDTO userFormDTO15 = new UserFormDTO("exampleuser15@email.com", "Laura", "Laura Pérez", "Seville", "Spain", "password", "password");
        UserFormDTO userFormDTO16 = new UserFormDTO("exampleuser16@email.com", "Oliver", "Oliver Smith", "Bristol", "United Kingdom", "password", "password");

        UserDTO user = userService.createUser(userFormDTO);
        usersId.put(2L, user.id());

        user = userService.createUser(userFormDTO1);
        usersId.put(3L, user.id());

        user = userService.createUser(userFormDTO2);
        usersId.put(4L, user.id());

        user = userService.createUser(userFormDTO3);
        usersId.put(5L, user.id());

        user = userService.createUser(userFormDTO4);
        usersId.put(6L, user.id());

        user = userService.createUser(userFormDTO5);
        usersId.put(7L, user.id());

        user = userService.createUser(userFormDTO6);
        usersId.put(8L, user.id());

        user = userService.createUser(userFormDTO7);
        usersId.put(9L, user.id());

        user = userService.createUser(userFormDTO8);
        usersId.put(10L, user.id());

        user = userService.createUser(userFormDTO9);
        usersId.put(11L, user.id());

        user = userService.createUser(userFormDTO10);
        usersId.put(12L, user.id());

        user = userService.createUser(userFormDTO11);
        usersId.put(13L, user.id());

        user = userService.createUser(userFormDTO12);
        usersId.put(14L, user.id());

        user = userService.createUser(userFormDTO13);
        usersId.put(15L, user.id());

        user = userService.createUser(userFormDTO14);
        usersId.put(16L, user.id());

        user = userService.createUser(userFormDTO15);
        usersId.put(17L, user.id());

        user = userService.createUser(userFormDTO16);
        usersId.put(18L, user.id());
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

        CityDTO cityDTO = cityService.addCity(cityFormDTO1).city();
        citiesId.put(1L, cityDTO.id());

        cityDTO = cityService.addCity(cityFormDTO2).city();
        citiesId.put(2L, cityDTO.id());

        cityDTO = cityService.addCity(cityFormDTO3).city();
        citiesId.put(3L, cityDTO.id());

        cityDTO = cityService.addCity(cityFormDTO4).city();
        citiesId.put(4L, cityDTO.id());

        cityDTO = cityService.addCity(cityFormDTO5).city();
        citiesId.put(5L, cityDTO.id());

        cityDTO = cityService.addCity(cityFormDTO6).city();
        citiesId.put(6L, cityDTO.id());

        cityDTO = cityService.addCity(cityFormDTO7).city();
        citiesId.put(7L, cityDTO.id());

        cityDTO = cityService.addCity(cityFormDTO8).city();
        citiesId.put(8L, cityDTO.id());
    }

    private void initiateExperiences() {
        ExperienceFormDTO experienceFormDTO1 = new ExperienceFormDTO(7.8F, "Finding accommodation in Paris", "Finding a room in Paris was challenging at first, but university groups and student housing websites helped me find a good place close to campus.", LocalDate.now().minusDays(13), List.of("Accommodation", "Studies"), citiesId.get(2L));
        ExperienceFormDTO experienceFormDTO2 = new ExperienceFormDTO(8.8F, "Exploring Paris with Erasmus friends", "Exploring different neighbourhoods with other Erasmus students has been one of the best parts of my Erasmus experience so far.", LocalDate.now().minusDays(3), List.of("Culture", "Social_Events"), citiesId.get(2L));

        ExperienceFormDTO experienceFormDTO3 = new ExperienceFormDTO(8.0F, "Getting used to university in Paris", "The university environment was different from what I was used to, but the international atmosphere made it easy to adapt.", LocalDate.now().minusDays(11), List.of("Studies", "Personal_Experience"), citiesId.get(2L));
        ExperienceFormDTO experienceFormDTO4 = new ExperienceFormDTO(8.7F, "Student life in Paris", "There are many activities for international students and it has been very easy to meet people from different countries.", LocalDate.now().minusDays(1), List.of("Social_Events", "Personal_Experience"), citiesId.get(2L));


        ExperienceFormDTO experienceFormDTO5 = new ExperienceFormDTO(8.8F, "Discovering Rome on foot", "Walking around Rome after classes became one of my favourite parts of the Erasmus. There was always another square, monument or neighbourhood to discover.", LocalDate.now().minusDays(13), List.of("Culture", "Personal_Experience"), citiesId.get(3L));
        ExperienceFormDTO experienceFormDTO6 = new ExperienceFormDTO(8.4F, "Italian food beyond the tourist areas", "Trying small restaurants away from the main tourist attractions was one of the best decisions I made. I discovered several inexpensive places popular with students.", LocalDate.now().minusDays(4), List.of("Gastronomy", "Culture"), citiesId.get(3L));

        ExperienceFormDTO experienceFormDTO7 = new ExperienceFormDTO(8.9F, "Finding an apartment in Rome", "The apartment search took some time because many offers were either expensive or far from the university. Sharing a flat with other students worked well in the end.", LocalDate.now().minusDays(10), List.of("Accommodation", "Personal_Experience"), citiesId.get(3L));
        ExperienceFormDTO experienceFormDTO8 = new ExperienceFormDTO(8.2F, "A weekend trip from Rome", "Living in Rome made it easy to organise weekend trips to other parts of Italy. Travelling with Erasmus friends was a great way to meet people from different countries.", LocalDate.now().minusDays(2), List.of("Social_Events", "Transportation", "Personal_Experience"), citiesId.get(3L));


        ExperienceFormDTO experienceFormDTO9 = new ExperienceFormDTO(8.5F, "Getting around Copenhagen", "Cycling became my main way of getting around Copenhagen. The city is very convenient for students who want to use a bicycle for everyday journeys.", LocalDate.now().minusDays(12), List.of("Transportation", "Personal_Experience"), citiesId.get(1L));
        ExperienceFormDTO experienceFormDTO10 = new ExperienceFormDTO(8.7F, "The international student community", "The Erasmus community in Copenhagen was very welcoming. University events and student organisations made it easy to meet people during the first weeks.", LocalDate.now().minusDays(5), List.of("Social_Events", "Culture"), citiesId.get(1L));

        ExperienceFormDTO experienceFormDTO11 = new ExperienceFormDTO(7.9F, "Preparing the Erasmus documents", "There was quite a lot of paperwork before leaving for Denmark. Creating a checklist with deadlines helped me avoid forgetting important documents.", LocalDate.now().minusDays(9), List.of("Documentation", "Studies"), citiesId.get(1L));
        ExperienceFormDTO experienceFormDTO12 = new ExperienceFormDTO(8.4F, "Studying in Copenhagen", "The teaching style was more focused on participation and group work than I expected. It took some time to adapt, but I ended up enjoying the experience.", LocalDate.now().minusDays(2), List.of("Studies", "Personal_Experience"), citiesId.get(1L));


        ExperienceFormDTO experienceFormDTO13 = new ExperienceFormDTO(7.2F, "Finding student accommodation in Barcelona", "Finding accommodation took some effort, but student groups and university recommendations helped me find a shared apartment near campus.", LocalDate.now().minusDays(13), List.of("Accommodation", "Studies"), citiesId.get(4L));
        ExperienceFormDTO experienceFormDTO14 = new ExperienceFormDTO(9.2F, "Exploring Barcelona with Erasmus friends", "Exploring different neighbourhoods with other Erasmus students has been one of the highlights of my stay so far.", LocalDate.now().minusDays(6), List.of("Culture", "Social_Events"), citiesId.get(4L));

        ExperienceFormDTO experienceFormDTO15 = new ExperienceFormDTO(7.5F, "Getting around Barcelona", "The public transport network made it easy to move around the city, although I ended up walking most of the time.", LocalDate.now().minusDays(10), List.of("Transportation", "Personal_Experience"), citiesId.get(4L));
        ExperienceFormDTO experienceFormDTO16 = new ExperienceFormDTO(9.0F, "Student life in Barcelona", "There are many activities for international students and it has been very easy to meet people from different countries.", LocalDate.now().minusDays(4), List.of("Social_Events", "Personal_Experience"), citiesId.get(4L));

        ExperienceFormDTO experienceFormDTO17 = new ExperienceFormDTO(7.8F, "University life in Barcelona", "The international environment at university made it easy to meet students from different countries and adapt to the new academic environment.", LocalDate.now().minusDays(12), List.of("Studies", "Social_Events"), citiesId.get(4L));
        ExperienceFormDTO experienceFormDTO18 = new ExperienceFormDTO(9.4F, "Weekend at the beach", "Having beaches so close to the city makes weekends very different from my usual university routine.", LocalDate.now().minusDays(1), List.of("Personal_Experience", "Culture"), citiesId.get(4L));


        ExperienceFormDTO experienceFormDTO19 = new ExperienceFormDTO(7.8F, "Finding accommodation in Berlin", "The housing market was competitive, but university groups and shared apartments were useful for finding a room.", LocalDate.now().minusDays(13), List.of("Accommodation", "Documentation"), citiesId.get(5L));
        ExperienceFormDTO experienceFormDTO20 = new ExperienceFormDTO(8.6F, "Exploring Berlin neighbourhoods", "Each neighbourhood has a very different atmosphere, which makes exploring the city especially interesting.", LocalDate.now().minusDays(6), List.of("Culture", "Personal_Experience"), citiesId.get(5L));

        ExperienceFormDTO experienceFormDTO21 = new ExperienceFormDTO(8.0F, "Public transport in Berlin", "The public transport network is extensive and useful for getting around the city and visiting different neighbourhoods.", LocalDate.now().minusDays(10), List.of("Transportation", "Personal_Experience"), citiesId.get(5L));
        ExperienceFormDTO experienceFormDTO22 = new ExperienceFormDTO(8.8F, "Museums in Berlin", "The number of museums and historical places to visit is impressive, especially for students interested in European history.", LocalDate.now().minusDays(3), List.of("Culture", "Personal_Experience"), citiesId.get(5L));

        ExperienceFormDTO experienceFormDTO23 = new ExperienceFormDTO(8.1F, "Student life in Berlin", "There are many student organisations and international events, although the city can feel quite large at first.", LocalDate.now().minusDays(11), List.of("Social_Events", "Studies"), citiesId.get(5L));
        ExperienceFormDTO experienceFormDTO24 = new ExperienceFormDTO(8.7F, "Erasmus events in Berlin", "International student events have made it much easier to meet people and discover the city.", LocalDate.now().minusDays(2), List.of("Social_Events", "Culture"), citiesId.get(5L));


        ExperienceFormDTO experienceFormDTO25 = new ExperienceFormDTO(8.0F, "Cycling in Amsterdam", "Cycling is one of the easiest ways to get around Amsterdam and quickly became part of my daily routine.", LocalDate.now().minusDays(12), List.of("Transportation", "Personal_Experience"), citiesId.get(6L));
        ExperienceFormDTO experienceFormDTO26 = new ExperienceFormDTO(8.6F, "Exploring Amsterdam by bike", "Cycling around the canals and different neighbourhoods has been one of my favourite parts of living here.", LocalDate.now().minusDays(5), List.of("Transportation", "Culture"), citiesId.get(6L));

        ExperienceFormDTO experienceFormDTO27 = new ExperienceFormDTO(8.2F, "Finding student accommodation", "Accommodation was expensive, but sharing a flat with other students made it more manageable.", LocalDate.now().minusDays(9), List.of("Accommodation"), citiesId.get(6L));
        ExperienceFormDTO experienceFormDTO28 = new ExperienceFormDTO(8.7F, "Student events in Amsterdam", "There are many events organised for international students, making it easy to meet people.", LocalDate.now().minusDays(2), List.of("Social_Events"), citiesId.get(6L));


        ExperienceFormDTO experienceFormDTO29 = new ExperienceFormDTO(7.4F, "Finding accommodation in Lisbon", "The accommodation search took some time, but student groups were useful for finding shared apartments.", LocalDate.now().minusDays(13), List.of("Accommodation"), citiesId.get(7L));
        ExperienceFormDTO experienceFormDTO30 = new ExperienceFormDTO(8.8F, "Student life in Lisbon", "Lisbon has a relaxed atmosphere and the international student community is very welcoming.", LocalDate.now().minusDays(4), List.of("Personal_Experience", "Social_Events"), citiesId.get(7L));

        ExperienceFormDTO experienceFormDTO31 = new ExperienceFormDTO(8.6F, "Exploring Lisbon", "The city is full of viewpoints, historic streets and affordable places to eat.", LocalDate.now().minusDays(7), List.of("Culture", "Gastronomy"), citiesId.get(7L));
        ExperienceFormDTO experienceFormDTO32 = new ExperienceFormDTO(9.0F, "Weekend activities in Lisbon", "There are many places to visit and the city's relaxed atmosphere makes it easy to enjoy weekends.", LocalDate.now().minusDays(1), List.of("Culture", "Personal_Experience"), citiesId.get(7L));


        ExperienceFormDTO experienceFormDTO33 = new ExperienceFormDTO(7.9F, "Living in Prague as an Erasmus student", "Prague is relatively affordable compared with other European capitals and there are many students in the city.", LocalDate.now().minusDays(13), List.of("Personal_Experience", "Studies"), citiesId.get(8L));
        ExperienceFormDTO experienceFormDTO34 = new ExperienceFormDTO(8.5F, "Exploring Prague", "The historic centre is beautiful and there are many interesting places to discover outside the main tourist areas.", LocalDate.now().minusDays(6), List.of("Culture", "Personal_Experience"), citiesId.get(8L));

        ExperienceFormDTO experienceFormDTO35 = new ExperienceFormDTO(8.1F, "Public transport in Prague", "The public transport system is convenient and makes it easy to get to university and explore the city.", LocalDate.now().minusDays(10), List.of("Transportation"), citiesId.get(8L));
        ExperienceFormDTO experienceFormDTO36 = new ExperienceFormDTO(8.7F, "Erasmus events in Prague", "International events organised by students are a great way to meet people and explore the city together.", LocalDate.now().minusDays(3), List.of("Social_Events", "Culture"), citiesId.get(8L));


        Authentication authentication = new UsernamePasswordAuthenticationToken("exampleuser1@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ExperienceDTO experienceDTO = experienceService.createExperience(experienceFormDTO1);
        experiencesId.put(1L, experienceDTO.id());
        experienceDTO = experienceService.createExperience(experienceFormDTO2);
        experiencesId.put(2L, experienceDTO.id());


        authentication = new UsernamePasswordAuthenticationToken("exampleuser2@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceDTO = experienceService.createExperience(experienceFormDTO5);
        experiencesId.put(5L, experienceDTO.id());
        experienceDTO = experienceService.createExperience(experienceFormDTO6);
        experiencesId.put(6L, experienceDTO.id());


        authentication = new UsernamePasswordAuthenticationToken("exampleuser3@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceDTO = experienceService.createExperience(experienceFormDTO9);
        experiencesId.put(9L, experienceDTO.id());
        experienceDTO = experienceService.createExperience(experienceFormDTO10);
        experiencesId.put(10L, experienceDTO.id());


        authentication = new UsernamePasswordAuthenticationToken("exampleuser4@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceDTO = experienceService.createExperience(experienceFormDTO19);
        experiencesId.put(19L, experienceDTO.id());
        experienceDTO = experienceService.createExperience(experienceFormDTO20);
        experiencesId.put(20L, experienceDTO.id());


        authentication = new UsernamePasswordAuthenticationToken("exampleuser5@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceDTO = experienceService.createExperience(experienceFormDTO13);
        experiencesId.put(13L, experienceDTO.id());
        experienceDTO = experienceService.createExperience(experienceFormDTO14);
        experiencesId.put(14L, experienceDTO.id());


        authentication = new UsernamePasswordAuthenticationToken("exampleuser6@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceDTO = experienceService.createExperience(experienceFormDTO25);
        experiencesId.put(25L, experienceDTO.id());
        experienceDTO = experienceService.createExperience(experienceFormDTO26);
        experiencesId.put(26L, experienceDTO.id());


        authentication = new UsernamePasswordAuthenticationToken("exampleuser7@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceDTO = experienceService.createExperience(experienceFormDTO33);
        experiencesId.put(33L, experienceDTO.id());
        experienceDTO = experienceService.createExperience(experienceFormDTO34);
        experiencesId.put(34L, experienceDTO.id());
        experienceDTO = experienceService.createExperience(experienceFormDTO35);
        experiencesId.put(35L, experienceDTO.id());
        experienceDTO = experienceService.createExperience(experienceFormDTO36);
        experiencesId.put(36L, experienceDTO.id());


        authentication = new UsernamePasswordAuthenticationToken("exampleuser8@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceDTO = experienceService.createExperience(experienceFormDTO29);
        experiencesId.put(29L, experienceDTO.id());
        experienceDTO = experienceService.createExperience(experienceFormDTO30);
        experiencesId.put(30L, experienceDTO.id());
        experienceDTO = experienceService.createExperience(experienceFormDTO31);
        experiencesId.put(31L, experienceDTO.id());
        experienceDTO = experienceService.createExperience(experienceFormDTO32);
        experiencesId.put(32L, experienceDTO.id());


        authentication = new UsernamePasswordAuthenticationToken("exampleuser9@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceDTO = experienceService.createExperience(experienceFormDTO15);
        experiencesId.put(15L, experienceDTO.id());
        experienceDTO = experienceService.createExperience(experienceFormDTO16);
        experiencesId.put(16L, experienceDTO.id());


        authentication = new UsernamePasswordAuthenticationToken("exampleuser10@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceDTO = experienceService.createExperience(experienceFormDTO17);
        experiencesId.put(17L, experienceDTO.id());
        experienceDTO = experienceService.createExperience(experienceFormDTO18);
        experiencesId.put(18L, experienceDTO.id());


        authentication = new UsernamePasswordAuthenticationToken("exampleuser11@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceDTO = experienceService.createExperience(experienceFormDTO21);
        experiencesId.put(21L, experienceDTO.id());
        experienceDTO = experienceService.createExperience(experienceFormDTO22);
        experiencesId.put(22L, experienceDTO.id());


        authentication = new UsernamePasswordAuthenticationToken("exampleuser12@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceDTO = experienceService.createExperience(experienceFormDTO23);
        experiencesId.put(23L, experienceDTO.id());
        experienceDTO = experienceService.createExperience(experienceFormDTO24);
        experiencesId.put(24L, experienceDTO.id());


        authentication = new UsernamePasswordAuthenticationToken("exampleuser13@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceDTO = experienceService.createExperience(experienceFormDTO27);
        experiencesId.put(27L, experienceDTO.id());
        experienceDTO = experienceService.createExperience(experienceFormDTO28);
        experiencesId.put(28L, experienceDTO.id());


        authentication = new UsernamePasswordAuthenticationToken("exampleuser14@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceDTO = experienceService.createExperience(experienceFormDTO7);
        experiencesId.put(7L, experienceDTO.id());
        experienceDTO = experienceService.createExperience(experienceFormDTO8);
        experiencesId.put(8L, experienceDTO.id());


        authentication = new UsernamePasswordAuthenticationToken("exampleuser15@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceDTO = experienceService.createExperience(experienceFormDTO3);
        experiencesId.put(3L, experienceDTO.id());
        experienceDTO = experienceService.createExperience(experienceFormDTO4);
        experiencesId.put(4L, experienceDTO.id());


        authentication = new UsernamePasswordAuthenticationToken("exampleuser16@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceDTO = experienceService.createExperience(experienceFormDTO11);
        experiencesId.put(11L, experienceDTO.id());
        experienceDTO = experienceService.createExperience(experienceFormDTO12);
        experiencesId.put(12L, experienceDTO.id());
    }

    private void initiateComments(){
        CommentFormDTO commentFormDTO = new CommentFormDTO("My opinion or point of view regarding the experience");

        Authentication authentication = new UsernamePasswordAuthenticationToken("exampleuser3@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        commentService.postComment(experiencesId.get(1L), commentFormDTO);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser2@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        commentService.postComment(experiencesId.get(3L), commentFormDTO);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser1@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        commentService.postComment(experiencesId.get(2L), commentFormDTO);

    }

    private void initiateProfileImages() throws IOException {
        MultipartFile image = new ResourceMultipartFile("profile_image1.png", "image/png");
        MultipartFile image2 = new ResourceMultipartFile("profile_image2.png", "image/png");

        Authentication authentication = new UsernamePasswordAuthenticationToken("exampleuser1@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addUserImage(usersId.get(3L), image);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser2@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addUserImage(usersId.get(4L), image2);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser3@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addUserImage(usersId.get(5L), image);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser4@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addUserImage(usersId.get(6L), image2);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser5@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addUserImage(usersId.get(7L), image);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser6@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addUserImage(usersId.get(8L), image2);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser7@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addUserImage(usersId.get(9L), image);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser8@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addUserImage(usersId.get(10L), image2);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser9@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addUserImage(usersId.get(11L), image2);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser10@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addUserImage(usersId.get(12L), image);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser11@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addUserImage(usersId.get(13L), image2);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser12@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addUserImage(usersId.get(14L), image);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser13@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addUserImage(usersId.get(15L), image2);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser14@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addUserImage(usersId.get(17L), image);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser15@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addUserImage(usersId.get(17L), image2);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser16@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addUserImage(usersId.get(18L), image);
    }

    private void initiateExperienceMultimedia() throws IOException {
        MultipartFile image = new ResourceMultipartFile("example_image.jpg", "image/jpeg");
        MultipartFile video = new ResourceMultipartFile("example_video1.mp4", "video/mp4");
        MultipartFile video2 = new ResourceMultipartFile("example_video2.mp4", "video/mp4");

        Authentication authentication = new UsernamePasswordAuthenticationToken("exampleuser1@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addMultimedia(experiencesId.get(1L), List.of(image, video2));
        multimediaService.addMultimedia(experiencesId.get(2L), List.of(video, video2));


        authentication = new UsernamePasswordAuthenticationToken("exampleuser2@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addMultimedia(experiencesId.get(5L), List.of(image, video));
        multimediaService.addMultimedia(experiencesId.get(6L), List.of(image));


        authentication = new UsernamePasswordAuthenticationToken("exampleuser3@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addMultimedia(experiencesId.get(9L), List.of(image, image));
        multimediaService.addMultimedia(experiencesId.get(10L), List.of(video));


        authentication = new UsernamePasswordAuthenticationToken("exampleuser4@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addMultimedia(experiencesId.get(19L), List.of(video, image));
        multimediaService.addMultimedia(experiencesId.get(20L),  List.of(image, video2));


        authentication = new UsernamePasswordAuthenticationToken("exampleuser5@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addMultimedia(experiencesId.get(13L), List.of(image, video));
        multimediaService.addMultimedia(experiencesId.get(14L), List.of(image, image));


        authentication = new UsernamePasswordAuthenticationToken("exampleuser6@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addMultimedia(experiencesId.get(25L), List.of(video2));
        multimediaService.addMultimedia(experiencesId.get(26L), List.of(video2, image));


        authentication = new UsernamePasswordAuthenticationToken("exampleuser7@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addMultimedia(experiencesId.get(33L), List.of(video));
        multimediaService.addMultimedia(experiencesId.get(36L), List.of(video, video2));
        multimediaService.addMultimedia(experiencesId.get(34L), List.of(image, video));
        multimediaService.addMultimedia(experiencesId.get(35L), List.of(video2, video));


        authentication = new UsernamePasswordAuthenticationToken("exampleuser8@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        multimediaService.addMultimedia(experiencesId.get(29L), List.of(image));
        multimediaService.addMultimedia(experiencesId.get(30L), List.of(image, video2));
        multimediaService.addMultimedia(experiencesId.get(31L), List.of(video, video2));
        multimediaService.addMultimedia(experiencesId.get(32L), List.of(image, video));
    }

    @PostConstruct
    @Transactional
    public void init(){

        commentService.emptyComments();
        multimediaService.emptyMultimedia();
        experienceService.emptyExperiences();
        cityService.emptyCities();
        userService.emptyUsers();

        initiateUsers();
        initiateCities();
        initiateExperiences();
        initiateComments();
        try {
            initiateProfileImages();
            initiateExperienceMultimedia();
        } catch (IOException e) {
            log.error("e: ", e);
        }
    }
}
