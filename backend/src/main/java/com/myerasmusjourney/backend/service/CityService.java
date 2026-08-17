package com.myerasmusjourney.backend.service;

import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.dto.CityDTO;
import com.myerasmusjourney.backend.dto.CityFormDTO;
import com.myerasmusjourney.backend.mapper.CityMapper;
import com.myerasmusjourney.backend.repository.CityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityMapper cityMapper;

    private String formatName(String value){
        if(value == null || value.isEmpty()){
            return value;
        }
        value = value.trim().toLowerCase();
        return Arrays.stream(value.trim().toLowerCase().split("\\s+"))
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
                .collect(Collectors.joining(" "));
    }

    @Transactional
    public ResponseEntity<CityDTO> addCity(CityFormDTO cityFormDTO){
        String cityName = formatName(cityFormDTO.name());
        List<City> cities = cityRepository.findByName(cityName);
        String countryName = formatName(cityFormDTO.country());
        for(City c: cities){
            if(c.getCountry().equals(countryName)) return ResponseEntity.ok(cityMapper.toDTO(c));
        }

        City city = new City(cityName, countryName, cityFormDTO.description());
        City savedCity = cityRepository.save(city);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedCity.getId()).toUri();
        return ResponseEntity.created(location).body(cityMapper.toDTO(savedCity));
    }
}
