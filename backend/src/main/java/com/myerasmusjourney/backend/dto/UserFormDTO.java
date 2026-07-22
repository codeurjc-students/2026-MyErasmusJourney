package com.myerasmusjourney.backend.dto;

public record UserFormDTO(
        String email,
        String displayName,
        String fullName,
        String password,
        String passwordConfirmation
) {}
