package com.myerasmusjourney.backend.dto;

public record CitySimpleDTO (
    Long id,
    String name,
    String description,
    String country
){}
