package com.myerasmusjourney.backend.mapper;

import com.myerasmusjourney.backend.domain.City;
import com.myerasmusjourney.backend.dto.CityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityDTO toDTO(City city);
}
