package com.myerasmusjourney.backend.controller;

import com.myerasmusjourney.backend.dto.ExperienceDTO;
import com.myerasmusjourney.backend.dto.ExperienceFormDTO;
import com.myerasmusjourney.backend.dto.ExperienceSimpleDTO;
import com.myerasmusjourney.backend.enumeration.Category;
import com.myerasmusjourney.backend.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @PostMapping("/")
    public ResponseEntity<ExperienceDTO> createExperience(@RequestBody ExperienceFormDTO experienceFormDTO){
        ExperienceDTO experienceDTO = experienceService.createExperience(experienceFormDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(experienceDTO.id()).toUri();
        return ResponseEntity.created(location).body(experienceDTO);
    }

    @GetMapping("/categories")
    public Category[] getCategories(){
        return experienceService.getCategories();
    }
}
