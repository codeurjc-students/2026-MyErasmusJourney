package com.myerasmusjourney.backend.controller;

import com.myerasmusjourney.backend.dto.*;
import com.myerasmusjourney.backend.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @GetMapping("/trending")
    public Collection<CitySimpleDTO> getTrendingCities(){
        return cityService.getTrendingCities();
    }

    @GetMapping("/{id}")
    public CityDTO getCity(@PathVariable Long id){
        return cityService.getCity(id);
    }

    @PostMapping("/")
    public ResponseEntity<CityDTO> createCity(@RequestBody CityFormDTO cityFormDTO){
        CityService.CityResult result = cityService.addCity(cityFormDTO);
        CityDTO cityDTO = result.city();
        if(!result.created()) return ResponseEntity.ok(result.city());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cityDTO.id()).toUri();
        return ResponseEntity.created(location).body(cityDTO);
    }


}
