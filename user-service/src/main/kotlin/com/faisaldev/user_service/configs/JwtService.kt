package com.faisaldev.user_service.configs;

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.io.FileInputStream
import java.security.KeyStore
import java.security.Key
import java.security.PrivateKey
import java.security.PublicKey
import java.util.*

@Component
class JwtService {

    @Value("\${security.jwt.key.name}")
    private lateinit var keyName: String

    @Value("\${security.jwt.key.alias}")
    private lateinit var keyAlias: String

    @Value("\${security.jwt.key.password}")
    private lateinit var keyPassword: String

    @Value("\${security.jwt.expiration-time}")
    private var jwtExpiration: Long = 0

    private fun getSignInKey(): PrivateKey {
        val keyStore: KeyStore = KeyStore.getInstance("PKCS12").apply {
            val resource: Resource = ClassPathResource(keyName)
            load(resource.inputStream, keyPassword.toCharArray())
        }
        return keyStore.getKey(keyAlias, keyPassword.toCharArray()) as PrivateKey
    }

    private fun getPublicKey(): PublicKey {
        val keyStore: KeyStore = KeyStore.getInstance("PKCS12").apply {
            val resource: Resource = ClassPathResource(keyName)
            load(resource.inputStream, keyPassword.toCharArray())
        }
        return keyStore.getCertificate(keyAlias).publicKey
    }

    fun extractUsername(token: String): String {
        return extractClaim(token) { it.subject }
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    fun generateToken(userDetails: UserDetails): String {
        return generateToken(emptyMap(), userDetails)
    }

    fun generateToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String {
        return buildToken(extraClaims, userDetails, jwtExpiration)
    }

    fun getExpirationTime(): Long {
        return jwtExpiration
    }

    private fun buildToken(
        extraClaims: Map<String, Any>,
        userDetails: UserDetails,
        expiration: Long
    ): String {
        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.RS256) // Use RS256 for RSA keys
            .compact()
    }

    fun isTokenValid(token: String, headerUsername: String): Boolean {
        val username = extractUsername(token)
        return username == headerUsername && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token) { it.expiration }
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(getPublicKey())
            .build()
            .parseClaimsJws(token)
            .body
    }
}
