package com.faisaldev.auth_server.kafka

import com.faisaldev.auth_server.dtos.ApprovalEmailDto
import com.faisaldev.auth_server.dtos.EmailNotification
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
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class SubscriberServiceImpl(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder : BCryptPasswordEncoder,
    private val streamBridge: StreamBridge
) {
    private val gson = Gson()
    private val scope = CoroutineScope(Dispatchers.IO)


    @Bean
    fun sendWorkflowApproversEmails(): (String) -> Unit = { message ->
        scope.launch {
            println("Received message: $message")
            val approvalEmailDto: ApprovalEmailDto = gson.fromJson(message, ApprovalEmailDto::class.java)
            val emailNotification = EmailNotification()

            val role = roleRepository.findByRoleName(approvalEmailDto.profile).awaitSingle()
            println("Role: $role")
            val approversList = userRepository.findAllByProfileId(role.profileId?.toLong() ?: 0).collectList().awaitFirst()
            println("Approvers List: $approversList")

            for (approver in approversList) {
                emailNotification.email = approver.email
                emailNotification.title = "Agent Connect Approval Request"

                // HTML Email message
                emailNotification.message = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f4f4f4;
                            margin: 0;
                            padding: 20px;
                        }
                        .container {
                            max-width: 600px;
                            margin: auto;
                            background-color: #ffffff;
                            border-radius: 5px;
                            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                            overflow: hidden;
                        }
                        .header {
                            background-color: #007BFF;
                            color: #ffffff;
                            padding: 20px;
                            text-align: center;
                        }
                        .content {
                            padding: 20px;
                            line-height: 1.6;
                        }
                        .button {
                            display: inline-block;
                            background-color: #007BFF;
                            color: #ffffff;
                            padding: 10px 20px;
                            text-decoration: none;
                            border-radius: 5px;
                            margin-top: 20px;
                        }
                        .footer {
                            text-align: center;
                            padding: 20px;
                            font-size: 12px;
                            color: #888888;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>Approval Request</h1>
                        </div>
                        <div class="content">
                            <p>You have a request that is pending your approval. Please log in to the portal to check.</p>
                            <a href="https://your-portal-link.com" class="button">Log In to Admin Portal</a>
                        </div>
                        <div class="footer">
                            <p>&copy; 2024 Faisal Dev. All rights reserved.</p>
                        </div>
                    </div>
                </body>
                </html>
            """.trimIndent()

                val emailMessage = gson.toJson(emailNotification)
                streamBridge.send("sidian-email-topic", emailMessage)
            }
        }
    }



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
            email = userDto.email,
            active = true,
            profileId = 123 // 123 represents Customer in Roles Table
        )
    }

    private suspend fun saveUser(user: User) {
        userRepository.save(user).subscribe()
    }



}