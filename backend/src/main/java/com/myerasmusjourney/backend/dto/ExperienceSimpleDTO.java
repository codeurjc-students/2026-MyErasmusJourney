package com.myerasmusjourney.backend.dto;


import java.time.LocalDate;

public record ExperienceSimpleDTO (
        Long id,
        LocalDate date,
        Float rating,
        String title,
        String description
){}
