package com.myerasmusjourney.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:63315", "http://localhost:5173", "https://myerasmusjourney-app.livelycoast-08d4ed6b.germanywestcentral.azurecontainerapps.io")
                        .allowedMethods("*")
                        .allowCredentials(true)
                        .allowedHeaders("*");
            }
        };
    }
}