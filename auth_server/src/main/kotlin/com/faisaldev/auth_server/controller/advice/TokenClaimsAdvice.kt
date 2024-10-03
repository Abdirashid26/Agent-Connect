package com.faisaldev.auth_server.controller.advice

import com.faisaldev.auth_server.configs.JwtService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@ControllerAdvice
@RestController
class TokenClaimsAdvice(
    private val jwtService: JwtService,
){


    @ModelAttribute("profile")
    fun extractProfileClaim(
        @RequestHeader(HttpHeaders.AUTHORIZATION, required = false) token: String?
    ): String? {
        return token?.let { extractProfileFromToken(it) }
    }

    private fun extractProfileFromToken(token: String): String? {
        return try {
            // Remove "Bearer " prefix if present and trim whitespace
            val cleanedToken = token.replace("Bearer ", "").split(",")[0].trim()

            // Print the cleaned token for debugging
            println("Cleaned Token: $cleanedToken")

            // Parse JWT using the public key
            val claims: Claims = Jwts.parser() // Use parserBuilder() for newer versions of JJWT
                .setSigningKey(jwtService.getPublicKey()) // Verify using the public key
                .build()
                .parseClaimsJws(cleanedToken)
                .body

            claims["profile"] as String?
        } catch (e: Exception) {
            println("EXTRACT CLAIM EXCEPTION: ${e.message}")
            null // Handle exceptions appropriately
        }
    }
}