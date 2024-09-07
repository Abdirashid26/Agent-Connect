package com.faisaldev.auth_server.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("tb_roles")
data class Role(
    @Id val id: Long? = null,
    var roleName: String,
    var description: String
)
