package com.myerasmusjourney.backend.mapper;

import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.dto.ExperienceDTO;
import com.myerasmusjourney.backend.dto.ExperienceSimpleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExperienceMapper {
    @Mapping(source = "city.name", target = "cityName")
    @Mapping(source = "city.country", target = "country")
    @Mapping(source = "author.displayName", target = "authorName")
    ExperienceSimpleDTO toSimpleDTO(Experience experience);
    List<ExperienceSimpleDTO> toDTOs(List<Experience> experiences);

    ExperienceDTO toDTO(Experience experience);
}
