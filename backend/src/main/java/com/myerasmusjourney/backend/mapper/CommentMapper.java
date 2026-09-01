package com.myerasmusjourney.backend.mapper;

import com.myerasmusjourney.backend.domain.Comment;
import com.myerasmusjourney.backend.dto.CommentDTO;
import com.myerasmusjourney.backend.dto.CommentSimpleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "author.displayName", target = "authorName")
    @Mapping(source = "experience.id", target = "experienceId")
    CommentSimpleDTO toSimpleDTO(Comment comment);
    Collection<CommentSimpleDTO> toSimpleDTOs(Collection<Comment> comments);

    @Mapping(source = "experience.city.name", target = "experience.cityName")
    @Mapping(source = "experience.city.country", target = "experience.country")
    @Mapping(source = "experience.author.displayName", target = "experience.authorName")
    CommentDTO toDTO(Comment comment);
}