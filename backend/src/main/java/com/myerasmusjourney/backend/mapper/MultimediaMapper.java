package com.myerasmusjourney.backend.mapper;

import com.myerasmusjourney.backend.domain.ExperienceMultimedia;
import com.myerasmusjourney.backend.domain.Multimedia;
import com.myerasmusjourney.backend.dto.ExperienceMultimediaDTO;
import com.myerasmusjourney.backend.dto.ExperienceMultimediaSimpleDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MultimediaMapper {
    List<ExperienceMultimediaSimpleDTO> toSimpleDTOs(List<ExperienceMultimedia> multimediaList);
    ExperienceMultimediaDTO toDTO(Multimedia multimedia);
}
