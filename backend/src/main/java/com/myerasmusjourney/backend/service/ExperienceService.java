package com.myerasmusjourney.backend.service;

import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.ExperienceDTO;
import com.myerasmusjourney.backend.dto.ExperienceFormDTO;
import com.myerasmusjourney.backend.dto.ExperienceSimpleDTO;
import com.myerasmusjourney.backend.enumeration.Category;
import com.myerasmusjourney.backend.mapper.ExperienceMapper;
import com.myerasmusjourney.backend.repository.ExperienceRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private ExperienceMapper experienceMapper;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;

    @PostConstruct
    @Transactional
    public void init(){
        List<Experience> experiences = List.of(
            new Experience("Experiencia 1", "Descripcion 1", 9F, Category.Social_Events.toString(), null, null),
            new Experience("Experiencia 2", "Descripcion 2", 8.67F, Category.Personal_Experience.toString(), null, null),
            new Experience("Experiencia 3", "Descripcion 3", 5.4F, Category.Accommodation.toString(), null, null),
            new Experience("Experiencia 4", "Descripcion 4", 0.9F, Category.Gastronomy.toString(), null, null)
        );
        experienceRepository.saveAll(experiences);
    }

    public List<ExperienceSimpleDTO> getAllExperiences() {
        return experienceMapper.toDTOs(experienceRepository.findAll());
    }

    public Category[] getCategories() {
        return Category.values();
    }

    @Transactional
    public ExperienceDTO createExperience(ExperienceFormDTO experienceFormDTO){
        City city = cityService.findById(experienceFormDTO.cityId());
        User user = userService.getLoggedUser();
        Experience experience = new Experience(experienceFormDTO.title(), experienceFormDTO.description(), experienceFormDTO.rating(), experienceFormDTO.category(), city, user);
        Experience savedExperience = experienceRepository.save(experience);
        cityService.addExperience(savedExperience, city);
        userService.addExperience(savedExperience, user);
        return experienceMapper.toDTO(experience);
    }
}
