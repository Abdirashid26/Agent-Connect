package com.faisaldev.user_service.schedulers

import com.faisaldev.user_service.repositories.OtpRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class OtpCleanupTask(private val otpRepository: OtpRepository) {

    @Scheduled(cron = "0 0 * * * ?") // Runs every hour
    suspend fun cleanupExpiredOtps() {
        val now = LocalDateTime.now()
        otpRepository.deleteAllByExpirationTimeBefore(now)
    }
}