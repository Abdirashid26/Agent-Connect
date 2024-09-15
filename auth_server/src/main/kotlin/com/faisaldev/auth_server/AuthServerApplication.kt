package com.faisaldev.auth_server

import com.faisaldev.auth_server.configs.BasicUserEntity
import com.faisaldev.auth_server.configs.BasicUserRepository
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.runBlocking
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootApplication
class AuthServerApplication{

	@Bean
	fun applicationRunner(
		userRepository: BasicUserRepository,
		passwordEncoder: PasswordEncoder
	): ApplicationRunner {
		return ApplicationRunner {
//			runBlocking {
//				val username = "faisaldev"
//				val password = "" //put your password here
//				val encodedPassword = passwordEncoder.encode(password)
//
//				// Check if the user already exists
//				val existingUser = userRepository.findByUsername(username).awaitFirstOrNull()
//
//				if (existingUser == null) {
//					// Save new user
//					val newUser = BasicUserEntity(
//						username = username,
//						password = encodedPassword
//					)
//					userRepository.save(newUser)
//				} else {
//					println("User with username '$username' already exists.")
//				}
//			}
		}
	}

}

fun main(args: Array<String>) {
	runApplication<AuthServerApplication>(*args)
}
