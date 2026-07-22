package com.myerasmusjourney.backend.controller;

import com.myerasmusjourney.backend.dto.ExperienceSimpleDTO;
import com.myerasmusjourney.backend.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/experiences")
public class ExperienceRestController {

    @Autowired
    private ExperienceService experienceService;

    @GetMapping("/")
    public List<ExperienceSimpleDTO> getExperiences(){
        return experienceService.getAllExperiences();
    }
}
