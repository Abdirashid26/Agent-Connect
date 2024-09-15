package com.faisaldev.auth_server.controller

import com.faisaldev.auth_server.dtos.LoginDto
import com.faisaldev.auth_server.dtos.LoginResponseDto
import com.faisaldev.auth_server.services.AuthService
import com.faisaldev.user_service.utils.GlobalResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/api/v1/agent-connect/auth-service/")
class AuthController(
    private val authService: AuthService,
){



    @PostMapping("/login")
    fun login(
        @RequestBody loginDto: LoginDto
    ) : Mono<ResponseEntity<GlobalResponse<LoginResponseDto>>>{

        return authService.login(loginDto)
            .map { res ->
                ResponseEntity.ok().body(res)
            }

    }


}