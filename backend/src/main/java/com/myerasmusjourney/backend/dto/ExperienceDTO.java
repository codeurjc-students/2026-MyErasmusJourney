package com.myerasmusjourney.backend.dto;

import com.myerasmusjourney.backend.enumeration.Category;

import java.time.LocalDate;

public record ExperienceDTO (
        Long id,
        LocalDate date,
        Float rating,
        String title,
        String description,
        Category category,
        CitySimpleDTO city,
        UserSimpleDTO author
){}
