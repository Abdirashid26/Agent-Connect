package com.faisaldev.wallet.service

import com.faisaldev.user_service.utils.GlobalResponse
import com.faisaldev.wallet.dto.ValidatePinDto

interface AuthService {

    suspend fun validatePin(validatePinDto: ValidatePinDto): GlobalResponse<String>

}