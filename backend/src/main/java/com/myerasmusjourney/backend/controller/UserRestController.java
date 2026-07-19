package com.myerasmusjourney.backend.controller;

import com.myerasmusjourney.backend.dto.UserDTO;
import com.myerasmusjourney.backend.dto.UserFormDTO;
import com.myerasmusjourney.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
}
