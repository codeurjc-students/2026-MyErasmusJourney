package com.myerasmusjourney.backend.controller;

import com.myerasmusjourney.backend.dto.*;
import com.myerasmusjourney.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {

    @Autowired
    UserService userService;

    @PostMapping("/")
    public ResponseEntity<Object> createUser(@RequestBody UserFormDTO newUser){
        UserDTO userDTO = userService.createUser(newUser);
        if (userDTO == null){
            return ResponseEntity.badRequest().body("Passwords don't match");
        }   
        if (userDTO.id() == null) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userDTO.id()).toUri();
        return ResponseEntity.created(location).body(userDTO);
    }

    @GetMapping("/me")
    public UserSimpleDTO getUserInfo(){
        return userService.getUserInfo();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id){
        UserDTO dto = userService.getUserById(id);
        if (dto == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser (@PathVariable Long id){
        UserDTO userDTO = userService.deleteUser(id);
        if (userDTO == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/{id}/experiences")
    public ResponseEntity<Collection<ExperienceSimpleDTO>> getExperiencesByUser(@PathVariable Long id){
        Collection<ExperienceSimpleDTO> experienceSimpleDTOs =  userService.getExperiences(id);
        if (experienceSimpleDTOs == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok(experienceSimpleDTOs);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<Collection<CommentSimpleDTO>> getCommentsByUser(@PathVariable Long id){
        Collection<CommentSimpleDTO> commentSimpleDTOS =  userService.getComments(id);
        if (commentSimpleDTOS == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok(commentSimpleDTOS);
    }
}
