package com.faisaldev.auth_server.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.relational.core.mapping.MappedCollection
import java.time.LocalDateTime

@Table("users")
data class User(
    @Id val id: Long? = null,
    var username: String,
    var password: String,
    var email: String,
    var active: Boolean = true,
    var blocked: Boolean = false,
    var createDate: LocalDateTime = LocalDateTime.now(),
    var lastModifiedDate: LocalDateTime = LocalDateTime.now(),
    var lastLogin: LocalDateTime? = null,
    @Column("profile_id")
    var profileId: Int = 1  //  Check the Roles table to see what the profileId represent
)


data class UserDto(
    val firstName : String,
    val lastName : String,
    val phoneNumber: String,
    val county : String,
    val idNumber : String,
    val estate : String,
    val password : String?,
    val email : String
)
