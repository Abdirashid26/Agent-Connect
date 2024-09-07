package com.faisaldev.user_service.error_handlers

import com.faisaldev.user_service.utils.GlobalResponse
import com.faisaldev.user_service.utils.GlobalStatus
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class GlobalErrorHandler {


    @ExceptionHandler
    fun handlesFileNotUploadedException(
        ex : GlobalException
    ) : ResponseEntity<GlobalResponse<Any>>{
        val errorResponse = GlobalResponse<Any>(
            status = GlobalStatus.FAILURE.status,  // Assuming GlobalStatus is an enum you've defined
            message = ex.message,
            data = null
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }


}