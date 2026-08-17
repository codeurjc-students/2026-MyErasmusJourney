package com.myerasmusjourney.backend.mapper;

import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.dto.CityDTO;
import com.myerasmusjourney.backend.dto.CitySimpleDTO;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityDTO toDTO(City city);
    Collection<CitySimpleDTO> toSimpleDTOs(Collection<City> cities);
}
