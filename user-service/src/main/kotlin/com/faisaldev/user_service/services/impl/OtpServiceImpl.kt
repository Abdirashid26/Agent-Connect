package com.faisaldev.user_service.services.impl

import com.faisaldev.user_service.models.Otp
import com.faisaldev.user_service.repositories.OtpRepository
import com.faisaldev.user_service.services.OtpService
import com.faisaldev.user_service.services.UserService
import com.faisaldev.user_service.utils.GlobalResponse
import com.faisaldev.user_service.utils.GlobalStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.random.Random


@Service
class OtpServiceImpl(
    private val otpRepository: OtpRepository,
    private val userService: UserService
): OtpService {
    override suspend fun generateOtp(phoneNumber: String) {
        var otp = generateRandomOtp()
        val newOtp = Otp(
            phoneNumber = phoneNumber,
            otpValue = otp,
            expirationTime = LocalDateTime.now().plusMinutes(10),
            used = false
        )
        otpRepository.save(newOtp)
        // post the OTP to the Notification Service
    }

    override suspend fun verifyOtp(phoneNumber: String, otpValue: String): GlobalResponse<Boolean> {
        val otp = otpRepository.findByPhoneNumberAndOtpValueAndUsedIsFalse(phoneNumber, otpValue) // Check for unused OTPs

        return if (otp == null) {
            // OTP not found or already used
            GlobalResponse(
                GlobalStatus.FAILURE.status,
                "OTP not found or already used",
                false
            )
        } else if (otp.expirationTime.isBefore(LocalDateTime.now())) {
            // OTP has expired
            GlobalResponse(
                GlobalStatus.FAILURE.status,
                "OTP has expired",
                false
            )
        } else {
            // OTP is valid
            otp.used = true
            otpRepository.save(otp)

            userService.activateUserAccount(phoneNumber)

            GlobalResponse(
                GlobalStatus.SUCCESS.status,
                "OTP validated successfully",
                true
            )
        }
    }


    private fun generateRandomOtp(length: Int = 6): String {
        val otpChars = "0123456789"
        return (1..length)
            .map { otpChars[Random.nextInt(otpChars.length)] }
            .joinToString("")
    }


}