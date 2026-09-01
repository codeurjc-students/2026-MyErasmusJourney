package com.myerasmusjourney.backend.controller;

import com.myerasmusjourney.backend.dto.*;
import com.myerasmusjourney.backend.enumeration.Category;
import com.myerasmusjourney.backend.service.CommentService;
import com.myerasmusjourney.backend.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1/experiences")
public class ExperienceRestController {

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public Page<ExperienceSimpleDTO> getExperiences(Pageable pageable){
        return experienceService.getAllExperiences(pageable);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createExperience(@RequestBody ExperienceFormDTO experienceFormDTO){
        ExperienceDTO experienceDTO = experienceService.createExperience(experienceFormDTO);
        if (experienceDTO == null) return ResponseEntity.badRequest().body("At least 1 category is needed and an experience can't have more than 3 categories.");
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(experienceDTO.id()).toUri();
        return ResponseEntity.created(location).body(experienceDTO);
    }

    @GetMapping("/categories")
    public Category[] getCategories(){
        return experienceService.getCategories();
    }

    @GetMapping("/{id}")
    public ExperienceDTO getExperienceById(@PathVariable Long id){
        return experienceService.getExperienceById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ExperienceDTO> deleteExperienceById(@PathVariable Long id){
        ExperienceDTO experienceDTO = experienceService.deleteExperienceById(id);
        if(experienceDTO == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok(experienceDTO);
    }

    @GetMapping("/{id}/comments")
    public Collection<CommentSimpleDTO> getComments(@PathVariable Long id){
        return experienceService.getComments(id);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDTO> postComment(@PathVariable Long id, @RequestBody CommentFormDTO commentFormDTO){
        CommentDTO commentDTO =  commentService.postComment(id, commentFormDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(location).body(commentDTO);
    }
}
