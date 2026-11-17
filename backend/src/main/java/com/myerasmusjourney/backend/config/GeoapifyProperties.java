package com.myerasmusjourney.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "geoapify")
public record GeoapifyProperties(
        String apiKey
) {}
