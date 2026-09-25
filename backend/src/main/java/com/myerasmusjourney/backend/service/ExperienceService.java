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

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
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

    private boolean isActionNotAllowed(User user, Experience experience){
        return !(user.getRoles().contains("ADMIN")||experience.getAuthor().getId().equals(user.getId()));
    }

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

    @Transactional
    public Collection<CommentSimpleDTO> getComments(Long id){
        Experience experience = getExperience(id);
        return commentMapper.toSimpleDTOs(experience.getComments());
    }

    public ExperienceDTO deleteExperienceById(Long id) {
        User user = userService.getLoggedUser();
        Experience experience = experienceRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Experience not found"));
        if(user == null || isActionNotAllowed(user, experience)) return null;
        ExperienceDTO deletedExperience = experienceMapper.toDTO(experience);
        experienceRepository.delete(experience);
        return deletedExperience;
    }

    public void emptyExperiences(){
        experienceRepository.deleteAll();
    }

    public Page<ExperienceSimpleDTO> filterExperiences(String from, String to, Float minimumRate, Float maximumRate, String cityName, List<String> categories, Pageable pageable) {
        if(minimumRate == null) minimumRate = 0F;
        if (maximumRate == null) maximumRate = 10F;
        Page<ExperienceSimpleDTO> experienceSimpleDTOPage;
        if(categories != null && !categories.isEmpty()){
            List<Category> categoryList = categories.stream().map(Category::valueOf).toList();
            experienceSimpleDTOPage = experienceRepository.findFilteredWithCategories(cityName, categoryList, categoryList.size(), minimumRate, maximumRate, from == null ? null : LocalDate.parse(from), to == null ? null : LocalDate.parse(to),  pageable).map(experienceMapper::toSimpleDTO);
        }
        else{
            experienceSimpleDTOPage =  experienceRepository.findFilteredWithoutCategories(cityName, minimumRate, maximumRate, from == null ? null : LocalDate.parse(from), to == null ? null : LocalDate.parse(to),  pageable).map(experienceMapper::toSimpleDTO);
        }
        return experienceSimpleDTOPage;
    }
}
