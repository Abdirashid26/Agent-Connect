package com.faisaldev.api_gateway.exceptions

import io.jsonwebtoken.ExpiredJwtException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ExpiredJwtException::class)
    fun expiredJwtExceptionHandler(exception : ExpiredJwtException) : ResponseEntity<String> {
        exception.printStackTrace()
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }

}