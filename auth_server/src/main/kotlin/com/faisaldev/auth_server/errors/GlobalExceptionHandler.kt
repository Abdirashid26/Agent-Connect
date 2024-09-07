package com.faisaldev.auth_server.errors

import com.faisaldev.user_service.utils.GlobalResponse
import com.faisaldev.user_service.utils.GlobalStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class GlobalExceptionHandler  {



    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleUserNotFoundException(exception : UsernameNotFoundException) : ResponseEntity<GlobalResponse<Any>>{
        return ResponseEntity.ok().body(
            GlobalResponse(GlobalStatus.FAILURE.status, exception.message ?: "Username not found exception",null)
        )
    }


}