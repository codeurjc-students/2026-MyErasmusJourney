package com.myerasmusjourney.backend.dto;


import java.time.LocalDate;

public record ExperienceSimpleDTO (
        Long id,
        LocalDate date,
        String title,
        String description
){}
