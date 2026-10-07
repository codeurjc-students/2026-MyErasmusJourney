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

import java.time.LocalDate;
import java.util.*;
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

    @Transactional
    public CityDTO getCity(Long id) {
        return cityMapper.toDTO(findById(id));
    }

    public City findById(Long id) {
        return cityRepository.findById(id).orElseThrow(() -> new NoSuchElementException("City not found"));
    }

    @Transactional
    public void addExperience(Experience savedExperience, City city) {
        city.addExperience(savedExperience);
        cityRepository.save(city);
    }

    public void emptyCities(){
        cityRepository.deleteAll();
    }

    @Transactional
    public List<CitySimpleDTO> getTrendingCities() {
        List <City> cities = cityRepository.findAll();
        List<Experience> experiences;
        List<Map.Entry<Double, City>> trendingCities = new ArrayList<>();
        HashMap<City, Float> publicationGrowth = new HashMap<>();
        HashMap<City, Float> ratingTrend = new HashMap<>();
        HashMap<City, Integer> nCityExperiences = new HashMap<>();

        int totalExperiences = 0;
        for (City city: cities){
            experiences = cityRepository.findRecentExperiencesOfCity(city.getName(), city.getCountry(), LocalDate.now().minusDays(14),LocalDate.now());
            float totalRecentRating = 0;
            float totalPreviousRating = 0;
            float cityRatingTrend = 0;
            float cityPublicationGrowth = 0;
            int previousExperiences = 0;
            int recentExperiences = 0;

            for(Experience experience: experiences){
                if (experience.getDate().isAfter(LocalDate.now().minusDays(8))){
                    totalRecentRating += experience.getRating();
                    recentExperiences++;
                }
                else{
                    totalPreviousRating += experience.getRating();
                    previousExperiences ++;
                }
            }

            if (!experiences.isEmpty()){
                float recentRating = totalRecentRating/recentExperiences;
                float previousRating = totalPreviousRating/previousExperiences;
                if (previousRating >0) cityRatingTrend = (recentRating/previousRating) - 1;
                else cityRatingTrend = Math.min(1, recentRating);
                if (previousExperiences>0) cityPublicationGrowth =  ((float) recentExperiences /previousExperiences) - 1;
                else cityPublicationGrowth = 1;
            }
            totalExperiences += experiences.size();
            ratingTrend.put(city, Math.max(0, cityRatingTrend));
            publicationGrowth.put(city, Math.max(0, cityPublicationGrowth));
            nCityExperiences.put(city, experiences.size());
        }

        for(City city: cities){
            Double trendingScore = 0.3 * publicationGrowth.get(city) + 0.3 * ((float) nCityExperiences.get(city) /totalExperiences) + 0.4 * ratingTrend.get(city);
            trendingCities.add(Map.entry(trendingScore, city));
        }
        trendingCities.sort(Map.Entry.<Double, City>comparingByKey().reversed());

        return cityMapper.toSimpleDTOs(trendingCities.stream().map(Map.Entry::getValue).toList().subList(0,4));
    }
}
