package com.myerasmusjourney.backend.service;

import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.dto.CityDTO;
import com.myerasmusjourney.backend.dto.CityFormDTO;
import com.myerasmusjourney.backend.dto.CitySimpleDTO;
import com.myerasmusjourney.backend.mapper.CityMapper;
import com.myerasmusjourney.backend.repository.CityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CityService {

    public record CityResult(
            CityDTO city,
            boolean created
    ) {}

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
    public CityResult addCity(CityFormDTO cityFormDTO){
        String cityName = formatName(cityFormDTO.name());
        List<City> cities = cityRepository.findByName(cityName);
        String countryName = formatName(cityFormDTO.country());
        for(City c: cities){
            if(c.getCountry().equals(countryName)) return new CityResult(cityMapper.toDTO(c), false);
        }

        City city = new City(cityName, countryName, cityFormDTO.description());
        City savedCity = cityRepository.save(city);

        return new CityResult(cityMapper.toDTO(savedCity),true);
    }

    public Collection<CitySimpleDTO> getCities() {
        List<City> cities = cityRepository.findAll();
        return cityMapper.toSimpleDTOs(cities);
    }

    public City findById(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new NoSuchElementException("City not found"));
    }

    @Transactional
    public void addExperience(Experience savedExperience, City city) {
        city.addExperience(savedExperience);
        cityRepository.save(city);
    }
}
