package com.myerasmusjourney.backend.controller;

import com.myerasmusjourney.backend.domain.Multimedia;
import com.myerasmusjourney.backend.service.MultimediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/multimedia")
public class MultimediaRestController {

    @Autowired
    private MultimediaService multimediaService;

    @GetMapping("/users/{id}")
    public ResponseEntity<Resource> getMedia(@PathVariable Long id) {
        Multimedia multimedia = multimediaService.getUserImageById(id);
        Resource file = multimediaService.loadFileAsResource(multimedia.getMultimediaFile());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(multimedia.getContentType()))
                .cacheControl(CacheControl.maxAge(Duration.ofDays(7)).cachePublic())
                .eTag(String.valueOf(id))
                .body(file);
    }
}
