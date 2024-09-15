package com.faisaldev.user_service.services

import com.faisaldev.user_service.models.User
import com.faisaldev.user_service.models.UserDto
import kotlinx.coroutines.flow.Flow
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Flux

interface UserService {

    suspend fun createUser(userDto : UserDto, idImages: Flux<FilePart>) : User?

    suspend fun activateUserAccount(phoneNumber : String) : User?

    suspend fun getUserProfile(username : String) : User?


}