package com.myerasmusjourney.backend.service;

import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.domain.Comment;
import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.domain.User;
import com.myerasmusjourney.backend.dto.*;
import com.myerasmusjourney.backend.enumeration.Category;
import com.myerasmusjourney.backend.mapper.CommentMapper;
import com.myerasmusjourney.backend.mapper.ExperienceMapper;
import com.myerasmusjourney.backend.repository.ExperienceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
public class ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private ExperienceMapper experienceMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;

    public Page<ExperienceSimpleDTO> getAllExperiences(Pageable pageable) {
        return experienceRepository.findAll(pageable).map(experienceMapper::toSimpleDTO);
    }

    public Category[] getCategories() {
        return Category.values();
    }

    @Transactional
    public ExperienceDTO createExperience(ExperienceFormDTO experienceFormDTO){
        if(experienceFormDTO.categories().isEmpty() || experienceFormDTO.categories().size()>3 || experienceFormDTO.rating()<0 || experienceFormDTO.rating()>10){
            return null;
        }
        City city = cityService.findById(experienceFormDTO.cityId());
        User user = userService.getLoggedUser();
        Experience experience = new Experience(experienceFormDTO.title(), experienceFormDTO.description(), experienceFormDTO.rating(), experienceFormDTO.date(), experienceFormDTO.categories(), city, user);
        Experience savedExperience = experienceRepository.save(experience);
        cityService.addExperience(savedExperience, city);
        userService.addExperience(savedExperience, user);
        return experienceMapper.toDTO(savedExperience);
    }

    public ExperienceDTO getExperienceById(Long id){
        Experience experience = experienceRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Experience not found"));
        return experienceMapper.toDTO(experience);
    }

    public Experience getExperience(Long id){
        return experienceRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Experience not found"));
    }

    public void addComment(Comment comment, Experience experience) {
        experience.addComment(comment);
        experienceRepository.save(experience);
    }

    public Collection<CommentSimpleDTO> getComments(Long id){
        Experience experience = getExperience(id);
        return commentMapper.toSimpleDTOs(experience.getComments());
    }
}
