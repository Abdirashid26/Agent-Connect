package com.faisaldev.api_gateway.config;


import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final String BASE_URI_PATH = "/api/v1/agent-connect/";

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
        System.out.println("Gateway Config Route: "+ routeLocatorBuilder.routes().toString());
        return routeLocatorBuilder.routes()
                .route(p -> p.path(BASE_URI_PATH+"user-service/**")
//                        .filters(f -> f.requestRateLimiter(c -> {
//                            c.setRateLimiter(new RedisRateLimiter(10, 10));
//                        }))
                        .uri("lb://USER-AGENT-CONNECT"))
                .build();
    }


}
