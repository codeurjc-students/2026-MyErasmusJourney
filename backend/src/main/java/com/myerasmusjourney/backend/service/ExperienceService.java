package com.myerasmusjourney.backend.service;

import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.dto.ExperienceSimpleDTO;
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

    @PostConstruct
    @Transactional
    public void init(){
        List<Experience> experiences = List.of(
            new Experience("Experiencia 1", "Descripcion 1"),
            new Experience("Experiencia 2", "Descripcion 2"),
            new Experience("Experiencia 3", "Descripcion 3"),
            new Experience("Experiencia 4", "Descripcion 4")
        );
        experienceRepository.saveAll(experiences);
    }

    public List<ExperienceSimpleDTO> getAllExperiences() {
        return experienceMapper.toDTOs(experienceRepository.findAll());
    }
}
