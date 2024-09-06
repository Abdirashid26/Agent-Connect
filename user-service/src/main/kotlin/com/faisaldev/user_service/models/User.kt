package com.faisaldev.user_service.models

import jakarta.persistence.Table
import java.time.LocalDate
import java.util.Date



@Table(name = "tb_users")
class User(
    val id : Long?,
    var firstName : String,
    var lastName:  String,
    var phoneNumber : String,
    var idBackImage : String,
    var idFrontImage : String,
    var county : String,
    var idNumber : String,
    var estate : String,
   // audit info
    var createDate : LocalDate = LocalDate.now(),
    var lastModifiedDate : LocalDate = LocalDate.now(),
    var softDelete : Boolean = false
)

data class UserDto(
    val firstName : String,
    val lastName : String,
    val phoneNumber: String,
    val county : String,
    val idNumber : String,
    val estate : String
)


fun User.toDto() : UserDto {
    val userDto = UserDto(
        firstName = this.firstName,
        lastName = this.lastName,
        phoneNumber = this.phoneNumber,
        county = this.county,
        idNumber = this.idNumber,
        estate = this.estate
    )
    return userDto
}