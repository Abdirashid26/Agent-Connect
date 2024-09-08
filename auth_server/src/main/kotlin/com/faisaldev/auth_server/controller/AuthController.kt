package com.faisaldev.auth_server.controller

import com.faisaldev.auth_server.dtos.LoginDto
import com.faisaldev.auth_server.dtos.LoginResponseDto
import com.faisaldev.user_service.utils.GlobalResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/agent-connect/auth-service/")
class AuthController {


    @PostMapping("/login")
    fun login(
        @RequestBody loginDto: LoginDto
    ) : ResponseEntity<GlobalResponse<LoginResponseDto?>>{

        return ResponseEntity.ok()
            .body(
                GlobalResponse(
                    "00",
                    "00",
                        null
                    )
            )


    }


}