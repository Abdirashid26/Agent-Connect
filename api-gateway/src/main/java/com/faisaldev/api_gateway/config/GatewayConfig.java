package com.faisaldev.api_gateway.config;


import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class GatewayConfig {

    private final String BASE_URI_PATH = "/api/v1/agent-connect/";

    @Value("${basic.auth.username}")
    private String username;

    @Value("${basic.auth.password}")
    private String password;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        // Properly format and encode the credentials
        String credentials = username + ":" + password;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        // Print the credentials for debugging (avoid doing this in production)
        System.out.println("Gateway Config Route: " + routeLocatorBuilder.routes().toString());
        System.out.println("Encoded Credentials: " + encodedCredentials);

        return routeLocatorBuilder.routes()
                .route(p -> p.path(BASE_URI_PATH + "user-service/**")
                        .uri("lb://USER-AGENT-CONNECT"))

                .route(p -> p.path(BASE_URI_PATH + "wallet-service/**")
                        .uri("lb://WALLET-SERVICE"))

                .route(p -> p.path(BASE_URI_PATH + "auth-service/**")
                        .filters(f -> f.addRequestHeader(
                                HttpHeaders.AUTHORIZATION,
                                "Basic " + encodedCredentials
                        ))
                        .uri("lb://AUTH-AGENT-CONNECT"))
                .build();
    }
}
