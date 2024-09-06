package com.faisaldev.user_service.services

import com.faisaldev.user_service.models.User
import com.faisaldev.user_service.models.UserDto
import org.springframework.web.multipart.MultipartFile

interface UserService {

    fun createUser(userDto : UserDto, idImages : List<MultipartFile>) : User?

}