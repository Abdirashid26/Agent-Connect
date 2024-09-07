package com.faisaldev.auth_server.repositories

import com.faisaldev.auth_server.models.User
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono


@Repository
interface UserRepository : ReactiveCrudRepository<User,Long> {
     fun findByUsername(username : String) : Mono<User>
}