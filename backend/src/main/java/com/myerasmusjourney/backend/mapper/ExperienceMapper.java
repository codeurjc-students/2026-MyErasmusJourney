package com.myerasmusjourney.backend.mapper;

import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.dto.ExperienceDTO;
import com.myerasmusjourney.backend.dto.ExperienceSimpleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CommentMapper.class, UserMapper.class, CityMapper.class})
public interface ExperienceMapper {
    ExperienceSimpleDTO toSimpleDTO(Experience experience);
    List<ExperienceSimpleDTO> toDTOs(List<Experience> experiences);

    ExperienceDTO toDTO(Experience experience);
}
