package com.myerasmusjourney.backend.dto;

public record UserFormDTO(
        String email,
        String displayName,
        String fullName,
        String city,
        String country,
        String password,
        String passwordConfirmation
) {}
