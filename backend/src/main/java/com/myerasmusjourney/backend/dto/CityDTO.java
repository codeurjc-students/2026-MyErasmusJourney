package com.myerasmusjourney.backend.dto;

import java.util.Collection;

public record CityDTO(
        Long id,
        String name,
        String description,
        String country,
        Collection<ExperienceSimpleDTO> experiences
) {}
