package com.faisaldev.auth_server.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.server.SecurityWebFilterChain
import reactor.core.publisher.Mono

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    private val userRepository: BasicUserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Value("\${basic.auth.username}")
    private lateinit var username: String

    @Value("\${basic.auth.password}")
    private lateinit var password: String

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .authorizeExchange { exchanges ->
                exchanges
                    .pathMatchers("/public/**").permitAll()
                    .anyExchange().authenticated()
            }
            .httpBasic { basicSpec ->
                basicSpec.authenticationManager(authenticationManager())
            }
            .formLogin { formLoginSpec -> formLoginSpec.disable() }
            .csrf { csrf -> csrf.disable() }
            .build()
    }

    @Bean
    fun authenticationManager(): ReactiveAuthenticationManager {
        return ReactiveAuthenticationManager { authentication ->
            userRepository.findByUsername(authentication.name)
                .filter { user -> passwordEncoder.matches(authentication.credentials.toString(), user.password) }
                .map { user ->
                    UsernamePasswordAuthenticationToken(
                        user.username,
                        user.password,
                        // You should provide authorities here if needed
                        emptyList() // Replace with appropriate authorities if you have any
                    )
                }
                .cast(Authentication::class.java)
                .switchIfEmpty(Mono.empty())
        }
    }


}

interface BasicUserRepository : ReactiveCrudRepository<BasicUserEntity, Long> {
    @Query("SELECT * FROM tb_basic_users WHERE username = :username")
    fun findByUsername(username: String): Mono<BasicUserEntity>
}

@Table("tb_basic_users")
data class BasicUserEntity(
    val id: Long? = null,
    val username: String,
    val password: String,
)
