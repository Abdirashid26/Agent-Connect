package com.faisaldev.user_service.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime


@Table("tb_otps")
data class Otp(
    @Id val id: Long? = null,
    val phoneNumber: String,
    val otpValue: String,
    var used : Boolean,
    val expirationTime: LocalDateTime
)



data class OtpDto(
    var phoneNumber: String = "",
    var otpValue: String = ""
)


fun Otp.toverifyOtpDto() : OtpDto{
    return OtpDto(
        this.phoneNumber,
        this.otpValue
    )
}