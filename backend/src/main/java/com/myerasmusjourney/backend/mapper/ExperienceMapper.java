package com.myerasmusjourney.backend.mapper;

import com.myerasmusjourney.backend.domain.Experience;
import com.myerasmusjourney.backend.dto.ExperienceDTO;
import com.myerasmusjourney.backend.dto.ExperienceSimpleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CommentMapper.class})
public interface ExperienceMapper {
    @Mapping(source = "city.name", target = "cityName")
    @Mapping(source = "city.country", target = "country")
    @Mapping(source = "author.displayName", target = "authorName")
    @Mapping(source = "author.profileImage.id", target = "authorImageId")
    ExperienceSimpleDTO toSimpleDTO(Experience experience);
    List<ExperienceSimpleDTO> toDTOs(List<Experience> experiences);

    @Mapping(source = "author.profileImage.id", target = "author.userImageId")
    @Mapping(source = "multimedia", target = "multimedia")
    ExperienceDTO toDTO(Experience experience);
}
