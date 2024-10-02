package com.faisaldev.auth_server.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("tb_roles")
data class Role(
    @Id val id: Long? = null,
    var roleName: String,
    var description: String,
    @Column("profile_id")
    var profileId : Int? = null,  // NB : remember to add this to Liquibase
    @Column("roles_list")
    var rolesList : String = "CUSTOMER,"  // Default role is ROLE_CUSTOMER // NB also remember to add this to liquibase
)
