package com.faisaldev.auth_server.dtos

data class LoginDto(
    val username : String,
    val password : String
)


data class LoginResponseDto(
    val username : String,
    val token : String,
)