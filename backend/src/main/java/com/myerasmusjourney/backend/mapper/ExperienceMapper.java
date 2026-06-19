package com.myerasmusjourney.backend.mapper;

import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.dto.ExperienceSimpleDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExperienceMapper {
    List<ExperienceSimpleDTO> toDTOs(List<Experience> experiences);
}
