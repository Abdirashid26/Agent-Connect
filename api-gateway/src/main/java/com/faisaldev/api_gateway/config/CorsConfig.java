package com.faisaldev.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000")); // Specify allowed origins
        config.setAllowedMethods(List.of("*"));                       // Specify allowed methods
        config.setAllowedHeaders(List.of("*"));                       // Specify allowed headers
        config.setAllowCredentials(false);                             // Allow credentials
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}