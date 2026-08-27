package com.myerasmusjourney.backend.dto;

public record CommentSimpleDTO(
        Long id,
        String description,
        String authorName
) {}
