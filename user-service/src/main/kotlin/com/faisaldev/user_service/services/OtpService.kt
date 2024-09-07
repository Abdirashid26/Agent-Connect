package com.faisaldev.user_service.services

import com.faisaldev.user_service.utils.GlobalResponse

interface OtpService {

    suspend fun generateOtp(phoneNumber : String)

    suspend fun verifyOtp(phoneNumber: String, otpValue : String) : GlobalResponse<Boolean>

}