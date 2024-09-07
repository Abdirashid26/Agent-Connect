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

    // Role IDs stored as a comma-separated string
    @Column("role_ids")
    var roleIds: String = ""
) {
    fun getRoleIdList(): List<Long> {
        return roleIds.split(",").mapNotNull { it.toLongOrNull() }
    }

    fun setRoleIdList(ids: List<Long>) {
        roleIds = ids.joinToString(",")
    }
}


data class UserDto(
    val firstName : String,
    val lastName : String,
    val phoneNumber: String,
    val county : String,
    val idNumber : String,
    val estate : String,
    val password : String?
)
