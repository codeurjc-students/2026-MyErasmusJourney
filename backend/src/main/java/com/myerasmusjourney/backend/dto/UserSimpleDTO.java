package com.myerasmusjourney.backend.dto;

public record UserSimpleDTO(
        Long id,
        String fullName,
        String displayName,
        String email
) {}
