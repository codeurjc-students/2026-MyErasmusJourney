package com.myerasmusjourney.backend.initializer;

import com.myerasmusjourney.backend.dto.*;
import com.myerasmusjourney.backend.service.CityService;
import com.myerasmusjourney.backend.service.CommentService;
import com.myerasmusjourney.backend.service.ExperienceService;
import com.myerasmusjourney.backend.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

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
        UserFormDTO userFormDTO1 = new UserFormDTO("exampleuser1@email.com", "Daniel", "Daniel Grimm", "Paris", "France", "password", "password");
        UserFormDTO userFormDTO2= new UserFormDTO("exampleuser2@email.com", "Maria", "Maria Garcia", "Rome", "Italy", "password", "password");
        UserFormDTO userFormDTO3= new UserFormDTO("exampleuser3@email.com", "Max", "Max Helmut", "Copenhagen", "Denmark", "password", "password");

        userService.createUser(userFormDTO1);
        userService.createUser(userFormDTO2);
        userService.createUser(userFormDTO3);

        CityFormDTO cityFormDTO1 = new CityFormDTO("Copenhagen", "Capital of Denmark", "Denmark");
        CityFormDTO cityFormDTO2 = new CityFormDTO("Paris", "Capital of France", "France");
        CityFormDTO cityFormDTO3= new CityFormDTO("Rome", "Capital of Italy", "Italy");

        cityService.addCity(cityFormDTO1);
        cityService.addCity(cityFormDTO2);
        cityService.addCity(cityFormDTO3);

        ExperienceFormDTO experienceFormDTO1 = new ExperienceFormDTO(5.4F, "Example experience 1", "Long description about an amazing adventure or small story", LocalDate.now(), List.of("Studies", "Documentation"), 1L);
        ExperienceFormDTO experienceFormDTO2 = new ExperienceFormDTO(9F, "Example experience 2", "Long description about an amazing adventure or small story", LocalDate.now(), List.of("Studies", "Documentation"), 2L);
        ExperienceFormDTO experienceFormDTO3 = new ExperienceFormDTO(3.1F, "Example experience 3", "Long description about an amazing adventure or small story", LocalDate.now(), List.of("Studies", "Documentation"), 3L);

        CommentFormDTO commentFormDTO = new CommentFormDTO("My opinion or point of view regarding the experience");

        Authentication authentication = new UsernamePasswordAuthenticationToken("exampleuser1@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceService.createExperience(experienceFormDTO1);

        authentication = new UsernamePasswordAuthenticationToken("exampleuser2@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceService.createExperience(experienceFormDTO2);


        authentication = new UsernamePasswordAuthenticationToken("exampleuser3@email.com", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        experienceService.createExperience(experienceFormDTO3);

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
