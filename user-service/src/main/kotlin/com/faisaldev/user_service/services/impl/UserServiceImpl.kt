package com.faisaldev.user_service.services.impl

import com.faisaldev.user_service.models.User
import com.faisaldev.user_service.models.UserDto
import com.faisaldev.user_service.repositories.UsersRepository
import com.faisaldev.user_service.services.UserService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile


@Service
class UserServiceImpl(
//    private val  usersRepository: UsersRepository
): UserService {


    override fun createUser(userDto: UserDto, idImages: List<MultipartFile>): User? {
        return null
    }


}