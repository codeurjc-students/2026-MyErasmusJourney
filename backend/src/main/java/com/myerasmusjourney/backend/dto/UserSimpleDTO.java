package com.myerasmusjourney.backend.dto;

public record UserSimpleDTO(
        Long id,
        String displayName,
        String email
) {}
