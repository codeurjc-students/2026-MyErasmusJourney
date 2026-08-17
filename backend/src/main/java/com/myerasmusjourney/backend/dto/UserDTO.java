package com.myerasmusjourney.backend.dto;

import java.util.List;

public record UserDTO(
        Long id,
        String fullName,
        String displayName,
        String email,
        String studyLocation,
        List<String> roles
) {}
