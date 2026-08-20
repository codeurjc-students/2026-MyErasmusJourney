package com.myerasmusjourney.backend.dto;


import com.myerasmusjourney.backend.enumeration.Category;

import java.time.LocalDate;
import java.util.Collection;

public record ExperienceSimpleDTO (
        Long id,
        LocalDate date,
        Float rating,
        String title,
        String description,
        Collection<Category> categories
){}
