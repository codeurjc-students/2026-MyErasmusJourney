package com.myerasmusjourney.backend.mapper;

import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.dto.CityDTO;
import com.myerasmusjourney.backend.dto.CitySimpleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = {ExperienceMapper.class})
public interface CityMapper {
    @Mapping(source = "averageRating", target = "averageRating")
    CityDTO toDTO(City city, Double averageRating);

    List<CitySimpleDTO> toSimpleDTOs(Collection<City> cities);
}
