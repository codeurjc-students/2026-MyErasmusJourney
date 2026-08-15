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
import java.util.List;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityMapper cityMapper;

    @Transactional
    public ResponseEntity<CityDTO> addCity(CityFormDTO cityFormDTO){
        List<City> cities = cityRepository.findByName(cityFormDTO.name());

        for(City c: cities){
            if(c.getCountry().equals(cityFormDTO.country())) return ResponseEntity.ok(cityMapper.toDTO(c));
        }

        City city = new City(cityFormDTO.name(), cityFormDTO.country(), cityFormDTO.description());
        City savedCity = cityRepository.save(city);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedCity.getId()).toUri();
        return ResponseEntity.created(location).body(cityMapper.toDTO(savedCity));
    }
}
