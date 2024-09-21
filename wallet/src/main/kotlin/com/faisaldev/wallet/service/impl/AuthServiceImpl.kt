package com.faisaldev.wallet.service.impl

import com.faisaldev.user_service.utils.GlobalResponse
import com.faisaldev.user_service.utils.GlobalStatus
import com.faisaldev.wallet.dto.ValidatePinDto
import com.faisaldev.wallet.service.AuthService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody


@Service
class AuthServiceImpl(
    private val webClientBuilder: WebClient.Builder,
    @Value("\${service.auth}") private val authServiceBaseUrl: String
) : AuthService {

    private val authServiceWebClient: WebClient = webClientBuilder
        .baseUrl(authServiceBaseUrl)
        .build()




    override suspend fun validatePin(validatePinDto: ValidatePinDto): GlobalResponse<String> {
        return try{
            authServiceWebClient.post()
                .uri("validate-pin")
                .bodyValue(validatePinDto)
                .retrieve()
                .awaitBody<GlobalResponse<String>>()
        }
        catch (ex : Exception){
            println("ERROR : ${ex.message}")
            GlobalResponse(
                status = GlobalStatus.FAILURE.status,
                message = "Failed to validate PIN: ${ex.message}",
                data = null
            )
        }
    }



}