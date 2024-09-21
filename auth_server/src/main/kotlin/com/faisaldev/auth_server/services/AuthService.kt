package com.faisaldev.auth_server.services

import com.faisaldev.auth_server.configs.JwtService
import com.faisaldev.auth_server.dtos.LoginDto
import com.faisaldev.auth_server.dtos.LoginResponseDto
import com.faisaldev.auth_server.repositories.RoleRepository
import com.faisaldev.auth_server.repositories.UserRepository
import com.faisaldev.user_service.utils.GlobalResponse
import com.faisaldev.user_service.utils.GlobalStatus
import org.apache.kafka.common.security.auth.Login
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers


@Service
class AuthService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: BCryptPasswordEncoder
) {


    fun findByUsername(username: String?): Mono<UserDetails> {
        return userRepository.findByUsername(username ?: "")
            .flatMap { user ->
                if (user != null) {
                    val roleIds = user.getRoleIdList() // Get list of role IDs from the user entity
                    if (roleIds.isEmpty()) {
                        Mono.error(UsernameNotFoundException("User has no roles assigned"))
                    } else {
                        roleRepository.findByIdIn(roleIds) // Fetch roles based on IDs
                            .collectList()
                            .map { roles ->
                                val roleNames = roles.map { it.roleName }
                                val userDetails = org.springframework.security.core.userdetails.User.withUsername(user.username)
                                    .password(user.password) // Assuming password is already encoded
                                    .roles(*roleNames.toTypedArray()) // Convert list to vararg array
                                    .build()
                                userDetails
                            }
                    }
                } else {
                    Mono.error(UsernameNotFoundException("User not found"))
                }
            }
    }


    fun login(loginDto: LoginDto): Mono<GlobalResponse<LoginResponseDto>> {
        return findByUsername(loginDto.username)
            .flatMap { user ->
                println("LOGIN: $user")

                // Compare raw password to encoded password using passwordEncoder.matches()
                val isPasswordValid = passwordEncoder.matches(loginDto.password, user.password)

                println("IS PASSWORD VALID: $isPasswordValid")

                if (!isPasswordValid) {
                    // Return a failure response if the password is invalid
                    Mono.just(GlobalResponse<LoginResponseDto>(
                        GlobalStatus.FAILURE.status,
                        "Pin validation failed",
                        null
                    ))
                } else {
                    // Generate JWT token and create login response if the password is valid
                    val token: String = jwtService.generateToken(
                        mapOf(
                            "username" to loginDto.username,
                            "roles" to user.authorities
                        ),
                        user
                    )

                    val loginResponse = LoginResponseDto(user.username, token)

                    Mono.just(GlobalResponse<LoginResponseDto>(
                        GlobalStatus.SUCCESS.status,
                        "Login is successful",
                        loginResponse
                    ))
                }
            }
            .switchIfEmpty(Mono.error(UsernameNotFoundException("User not found")))
    }



    fun validatePin(loginDto: LoginDto): Mono<GlobalResponse<String>> {
        return userRepository.findByUsername(loginDto.username)
            .map { user ->
                println("LOGIN: $user")

                // Compare raw password to encoded password using passwordEncoder.matches()
                val isPasswordValid = passwordEncoder.matches(loginDto.password, user.password)

                println("IS PASSWORD VALID: $isPasswordValid")


                if (!isPasswordValid) {
                    GlobalResponse(
                        GlobalStatus.FAILURE.status,
                        "Pin validation failed",
                        "Pin Validation Failed"
                    )
                } else {
                    GlobalResponse(
                        GlobalStatus.SUCCESS.status,
                        "Pin validated successfully",
                        "Pin validated"
                    )
                }
            }
            .switchIfEmpty(Mono.error(UsernameNotFoundException("User not found")))
    }



}