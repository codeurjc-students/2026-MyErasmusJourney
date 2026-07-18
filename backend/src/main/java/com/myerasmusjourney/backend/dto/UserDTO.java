package com.myerasmusjourney.backend.dto;

public record UserDTO(
        Long id,
        String fullName,
        String display,
        String email
) {}
