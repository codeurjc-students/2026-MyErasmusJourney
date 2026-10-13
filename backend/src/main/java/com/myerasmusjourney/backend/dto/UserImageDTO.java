package com.myerasmusjourney.backend.dto;

public record UserImageDTO(
        Long id,
        String contentType,
        UserSimpleDTO userSimpleDTO
) {}
