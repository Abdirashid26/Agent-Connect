package com.faisaldev.user_service.error_handlers

class GlobalException(
    val customMessage : String = "Sorry but we your file uploads failed"
) : RuntimeException() {

    override val message: String
        get() = customMessage
}