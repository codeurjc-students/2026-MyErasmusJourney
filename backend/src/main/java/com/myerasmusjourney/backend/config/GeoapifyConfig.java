package com.myerasmusjourney.backend.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GeoapifyProperties.class)
public class GeoapifyConfig {}