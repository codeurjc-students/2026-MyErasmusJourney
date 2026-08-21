package com.myerasmusjourney.backend.controller;

import com.myerasmusjourney.backend.dto.*;
import com.myerasmusjourney.backend.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/cities")
public class CityRestController {

    @Autowired
    private CityService cityService;

    @GetMapping("/")
    public Collection<CitySimpleDTO> getCities(){
        return cityService.getCities();
    }

    @PostMapping("/")
    public ResponseEntity<CityDTO> createCity(@RequestBody CityFormDTO cityFormDTO){
        return cityService.addCity(cityFormDTO);
    }
}
