package com.faisaldev.wallet.service.impl

import com.faisaldev.user_service.utils.GlobalResponse
import com.faisaldev.user_service.utils.GlobalStatus
import com.faisaldev.wallet.dto.ValidatePinDto
import com.faisaldev.wallet.service.AuthService
import com.google.gson.Gson
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody


@Service
class AuthServiceImpl(
    private val webClientBuilder: WebClient.Builder,
    private val gson: Gson,
    @Value("\${service.auth}") private val authServiceBaseUrl: String
) : AuthService {

    private val authServiceWebClient: WebClient = webClientBuilder
        .baseUrl(authServiceBaseUrl)
        .build()




    override suspend fun validatePin(validatePinDto: ValidatePinDto): GlobalResponse<String> {
        return try {
            // Logging the request payload
            println("Sending request to validate PIN: $validatePinDto")

            // WebClient call with logging of the raw response
            val response = authServiceWebClient.post()
                .uri("validate-pin")
                .bodyValue(validatePinDto)
                .retrieve()
                .bodyToMono(String::class.java) // Temporarily change to String for debugging
                .awaitSingle()

            println("Received response: $response") // Log the raw response

            // Deserialize manually using Gson
            val globalResponse = gson.fromJson(response, GlobalResponse::class.java) as GlobalResponse<String>

            return globalResponse
        } catch (ex: Exception) {
            // Handle exceptions gracefully and return a failure response
            GlobalResponse(
                status = GlobalStatus.FAILURE.status,
                message = "Failed to validate PIN: ${ex.message}",
                data = null
            )
        }
    }



}