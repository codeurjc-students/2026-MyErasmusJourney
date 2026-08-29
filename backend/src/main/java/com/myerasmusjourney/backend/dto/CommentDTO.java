package com.myerasmusjourney.backend.dto;

import java.time.LocalDate;

public record CommentDTO(
        Long id,
        LocalDate date,
        String description,
        UserSimpleDTO author,
        ExperienceSimpleDTO experience
) {}
