package com.faisaldev.auth_server.repositories

import com.faisaldev.auth_server.models.Role
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface RoleRepository : ReactiveCrudRepository<Role, Long> {
     fun findByIdIn(ids: List<Long>): Flux<Role>

     fun findByProfileId(profileId: Int): Mono<Role>

     fun findByRoleName(roleName: String): Mono<Role>

}
