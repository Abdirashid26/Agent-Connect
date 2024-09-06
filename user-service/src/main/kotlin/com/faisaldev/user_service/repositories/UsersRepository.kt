package com.faisaldev.user_service.repositories

import com.faisaldev.user_service.models.User
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository


@Repository
interface UsersRepository : CoroutineCrudRepository<User,Long> {

    suspend fun findByPhoneNumber(phoneNumber : String) : User?

}