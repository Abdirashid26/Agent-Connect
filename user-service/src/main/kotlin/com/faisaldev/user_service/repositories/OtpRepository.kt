package com.faisaldev.user_service.repositories

import com.faisaldev.user_service.models.Otp
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.time.LocalDateTime

interface OtpRepository : CoroutineCrudRepository<Otp, Long> {

    suspend fun findByPhoneNumberAndOtpValueAndUsedIsFalse(phoneNumber : String, otpValue : String) : Otp?
    suspend fun deleteAllByExpirationTimeBefore(expirationTime: LocalDateTime)



}