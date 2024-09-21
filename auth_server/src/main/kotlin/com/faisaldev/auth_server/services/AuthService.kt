package com.faisaldev.auth_server.services

import com.faisaldev.auth_server.configs.JwtService
import com.faisaldev.auth_server.dtos.LoginDto
import com.faisaldev.auth_server.dtos.LoginResponseDto
import com.faisaldev.auth_server.repositories.RoleRepository
import com.faisaldev.auth_server.repositories.UserRepository
import com.faisaldev.user_service.utils.GlobalResponse
import com.faisaldev.user_service.utils.GlobalStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


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


    fun login(loginDto: LoginDto) : Mono<GlobalResponse<LoginResponseDto>> {
        return findByUsername(loginDto.username)
            .map{ user ->
                println("LOGIN: $user")
                val token : String = jwtService.generateToken(
                    mapOf(
                    "username" to loginDto.username ,
                        "roles" to user.authorities
                        ),
                    user)

                val loginResponse = LoginResponseDto(user.username,token)

                return@map GlobalResponse(GlobalStatus.SUCCESS.status,"Login is successfull",loginResponse)
            }
            .switchIfEmpty(Mono.error(UsernameNotFoundException("User not found")))
    }

     fun validatePin(loginDto: LoginDto) : Mono<GlobalResponse<String>> {

         return findByUsername(loginDto.username)
             .map{ user ->
                 println("LOGIN: $user")
                 if (!passwordEncoder.encode(loginDto.password).equals(user.password)) {
                     return@map GlobalResponse(GlobalStatus.FAILURE.status,"Pin validation failed","Pin Validation Failed")
                 }
                 return@map GlobalResponse(GlobalStatus.SUCCESS.status,"Pin validated successfully","Pin validated")
             }
             .switchIfEmpty(Mono.error(UsernameNotFoundException("User not found")))

     }


}