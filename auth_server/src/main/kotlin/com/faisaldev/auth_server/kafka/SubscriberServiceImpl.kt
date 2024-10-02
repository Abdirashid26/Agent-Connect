package com.faisaldev.auth_server.kafka

import com.faisaldev.auth_server.models.User
import com.faisaldev.auth_server.models.UserDto
import com.faisaldev.auth_server.repositories.RoleRepository
import com.faisaldev.auth_server.repositories.UserRepository
import com.faisaldev.auth_server.utils.generateFourDigitPassword
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class SubscriberServiceImpl(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder : BCryptPasswordEncoder
) {
    private val gson = Gson()
    private val scope = CoroutineScope(Dispatchers.IO)


    @Bean
    fun createUserCredentials(): (String) -> Unit = { message ->
        println("Received message: $message")
        // Launch a coroutine in a non-blocking way
        scope.launch {
            val userDto: UserDto = gson.fromJson(message, UserDto::class.java)
            val user = mapToUserEntity(userDto)
            saveUser(user)
        }
    }

    // Map UserDto to User entity
    private fun mapToUserEntity(userDto: UserDto): User {
        val password = generateFourDigitPassword()
        println("GENERATED_TEST_PASSWORD : ${password}") // this will send a sms
        return User(
            username = userDto.phoneNumber,
            password =  passwordEncoder.encode(password), // You should hash this in a real use case
            email = "${userDto.firstName}.${userDto.lastName}@example.com",
            active = true,
            profileId = 123 // 123 represents Customer in Roles Table
        )
    }

    private suspend fun saveUser(user: User) {
        userRepository.save(user).subscribe()
    }



}