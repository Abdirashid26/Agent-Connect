package com.faisaldev.api_gateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
open class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {


    @Bean
    open fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .authorizeExchange { exchanges ->
                exchanges
                    .pathMatchers(
                        "/api/v1/agent-connect/auth-service/login",
                        "/api/v1/agent-connect/auth-service/validate-pin",
                        "/api/v1/agent-connect/user-service/generate-otp",
                        "/api/v1/agent-connect/user-service/verify-otp",
                        "/api/v1/agent-connect/user-service/createUserAccount"
                    ).permitAll()   // Requires authentication
                    .anyExchange().authenticated() // Public endpoints
            }
            .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)  // Add JWT authentication filter
            .httpBasic { httpBasicSpec -> httpBasicSpec.disable() }  // Disable Basic Authentication
            .formLogin { formLoginSpec -> formLoginSpec.disable() }  // Disable form login
            .csrf { csrf -> csrf.disable() }  // Disable CSRF
            .build()
    }

}
