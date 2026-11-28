package com.myerasmusjourney.backend.mapper;

import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.dto.CityDTO;
import com.myerasmusjourney.backend.dto.CitySimpleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = {ExperienceMapper.class})
public interface CityMapper {
    @Mapping(source = "averageRating", target = "averageRating")
    CityDTO toDTO(City city, Double averageRating);

    @Mapping(source = "averageRating", target = "averageRating")
    CitySimpleDTO toSimpleDTO(City city, Double averageRating);

    List<CitySimpleDTO> toSimpleDTOs(Collection<City> cities);


    default List<CitySimpleDTO> toSimpleDTOs(
            List<City> cities,
            List<Double> averageRatings
    ) {
        if (cities == null || averageRatings == null) {
            return null;
        }

        if (cities.size() != averageRatings.size()) {
            throw new IllegalArgumentException(
                    "Cities and average ratings must have the same size"
            );
        }

        List<CitySimpleDTO> result = new ArrayList<>();

        for (int i = 0; i < cities.size(); i++) {
            result.add(toSimpleDTO(cities.get(i), averageRatings.get(i)));
        }

        return result;
    }
}
