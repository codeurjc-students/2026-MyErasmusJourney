package com.myerasmusjourney.backend.mapper;

import com.myerasmusjourney.backend.domain.Comment;
import com.myerasmusjourney.backend.dto.CommentDTO;
import com.myerasmusjourney.backend.dto.CommentSimpleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring", uses = {ExperienceMapper.class, UserMapper.class})
public interface CommentMapper {
    @Mapping(source = "author.displayName", target = "authorName")
    CommentSimpleDTO toSimpleDTO(Comment comment);
    Collection<CommentSimpleDTO> toSimpleDTOs(Collection<Comment> comments);

    CommentDTO toDTO(Comment comment);
}
