package com.faisaldev.api_gateway.config

import io.jsonwebtoken.ExpiredJwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import reactor.core.publisher.Mono
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.server.WebFilterChain

@Component
class JwtAuthenticationFilter(
    @Autowired private val jwtService: JwtService
) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val authorizationHeader = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)

        return if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            val token = authorizationHeader.substring(7)

            try {
                if (jwtService.isTokenValid(token, exchange.request.headers.getFirst("username") ?: "Non-Existent")) {
                    val username = jwtService.extractUsername(token)
                    val authentication = UsernamePasswordAuthenticationToken(username, null, listOf(SimpleGrantedAuthority("USER")))
                    return Mono.just(authentication)
                        .flatMap { auth -> chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth)) }
                } else {
                    return chain.filter(exchange)
                }
            } catch (ex: ExpiredJwtException) {
                // Handle expired token
                exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                return exchange.response.setComplete() // Ends the request without continuing the chain
            } catch (ex: Exception) {
                // Handle any other JWT parsing exceptions
                exchange.response.statusCode = HttpStatus.BAD_REQUEST
                return exchange.response.setComplete()
            }
        } else {
            return chain.filter(exchange)
        }
    }
}

