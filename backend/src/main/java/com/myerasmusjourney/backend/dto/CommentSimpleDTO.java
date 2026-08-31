package com.myerasmusjourney.backend.dto;

import java.time.LocalDate;

public record CommentSimpleDTO(
        Long id,
        LocalDate date,
        String description,
        String authorName,
        Long experienceId
) {}
