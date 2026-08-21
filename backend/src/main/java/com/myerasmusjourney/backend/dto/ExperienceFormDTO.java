package com.myerasmusjourney.backend.dto;

import java.time.LocalDate;
import java.util.List;

public record ExperienceFormDTO(
        Float rating,
        String title,
        String description,
        LocalDate date,
        List<String> categories,
        Long cityId
) {}
