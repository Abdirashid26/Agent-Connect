package com.faisaldev.auth_server.services

import com.faisaldev.auth_server.repositories.RoleRepository
import com.faisaldev.auth_server.repositories.UserRepository
import kotlinx.coroutines.runBlocking
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class AuthService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository
): ReactiveUserDetailsService {
    override fun findByUsername(username: String?): Mono<UserDetails> {
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


}