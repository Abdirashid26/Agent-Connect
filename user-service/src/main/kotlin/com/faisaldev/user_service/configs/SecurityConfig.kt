package com.faisaldev.user_service.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
     private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {


    @Bean
    @Order(1) // Ensures this filter is the first to be reached
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .authorizeExchange { exchanges ->
                exchanges
                    .pathMatchers(
                        "/public/**",
                        "/api/v1/agent-connect/user-service/createUserAccount",
                        "/api/v1/agent-connect/user-service/verify-otp",
                        "/api/v1/agent-connect/user-service/generate-otp"
                    ).permitAll() // Public endpoints
                    .anyExchange().authenticated() // All other endpoints require authentication
            }
            .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .formLogin { formLoginSpec -> formLoginSpec.disable() } // Disable form login
            .csrf { csrf -> csrf.disable() } // Disable CSRF
            .build()
    }
}
