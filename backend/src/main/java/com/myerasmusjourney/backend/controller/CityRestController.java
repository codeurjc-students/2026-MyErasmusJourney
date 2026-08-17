package com.myerasmusjourney.backend.controller;

import com.myerasmusjourney.backend.dto.CityDTO;
import com.myerasmusjourney.backend.dto.CityFormDTO;
import com.myerasmusjourney.backend.dto.UserFormDTO;
import com.myerasmusjourney.backend.dto.UserSimpleDTO;
import com.myerasmusjourney.backend.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/cities")
public class CityRestController {

    @Autowired
    private CityService cityService;

    @PostMapping("/")
    public ResponseEntity<CityDTO> createCity(@RequestBody CityFormDTO cityFormDTO){
        return cityService.addCity(cityFormDTO);
    }
}
