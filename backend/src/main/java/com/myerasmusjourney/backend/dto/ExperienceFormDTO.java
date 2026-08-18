package com.myerasmusjourney.backend.dto;

public record ExperienceFormDTO(
        Float rating,
        String title,
        String description,
        String category,
        Long cityId
) {}
