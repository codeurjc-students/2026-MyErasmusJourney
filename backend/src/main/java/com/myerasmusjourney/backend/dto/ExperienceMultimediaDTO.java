package com.myerasmusjourney.backend.dto;

public record ExperienceMultimediaDTO(
        Long id,
        String contentType,
        ExperienceSimpleDTO experienceSimpleDTO
) {}
