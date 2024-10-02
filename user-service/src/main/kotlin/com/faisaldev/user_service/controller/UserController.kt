package com.faisaldev.user_service.controller

import com.faisaldev.user_service.error_handlers.GlobalException
import com.faisaldev.user_service.models.OtpDto
import com.faisaldev.user_service.models.User
import com.faisaldev.user_service.models.UserDto
import com.faisaldev.user_service.services.OtpService
import com.faisaldev.user_service.services.UserService
import com.faisaldev.user_service.utils.GlobalResponse
import com.faisaldev.user_service.utils.GlobalStatus
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Flux
import java.util.logging.Logger

@RestController
@RequestMapping("/api/v1/agent-connect/user-service")
class UserController(
    private val userService: UserService,
    private val otpService: OtpService,
    private val gson : Gson
){


    @PostMapping("/user")
    suspend fun getUserProfile(
        @RequestHeader("username") username: String
    )  : ResponseEntity<GlobalResponse<User?>>{
        println("USERNAME : ${username}")
        val userProfile = userService.getUserProfile(username)
        return  if (userProfile == null){
            ResponseEntity.ok(GlobalResponse(GlobalStatus.SUCCESS.status, "User does not exist", null))
        }else{
            ResponseEntity.ok(GlobalResponse(GlobalStatus.SUCCESS.status, "User profile info", userProfile))
        }
    }


    @PostMapping("/createUserAccount", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    suspend fun createUserAccount(
        @RequestPart("data") data: String, // JSON object
        @RequestPart("images") images: Flux<FilePart> // List of images
    ): ResponseEntity<GlobalResponse<UserDto>> {
        val userDto = gson.fromJson(data, UserDto::class.java)

        return try {
            val createdUser = userService.createUser(userDto, images)
            if (createdUser != null) {
                otpService.generateOtp(userDto.phoneNumber)
                ResponseEntity.ok(GlobalResponse(GlobalStatus.SUCCESS.status, "User created successfully", userDto))
            } else {
                throw GlobalException("User creation failed. No files were processed.")
            }
        } catch (ex: GlobalException) {
            throw ex
        } catch (ex: Exception) {
            println("ERROR: " + ex.localizedMessage)
            throw GlobalException("User creation failed due to an unknown error.")
        }
    }



    @PostMapping("/verify-otp")
    suspend fun verifyOtp(
        @RequestBody verifyOtp : OtpDto
    ) : ResponseEntity<GlobalResponse<Boolean>>{
        val globalResponse = otpService.verifyOtp(verifyOtp.phoneNumber, verifyOtp.otpValue)
        return ResponseEntity.ok().body(globalResponse)
    }


    @PostMapping("/generate-otp")
    suspend fun generateOtp(
        @RequestBody generateOtp : OtpDto
    ) : ResponseEntity<GlobalResponse<String>>{

        otpService.generateOtp(generateOtp.phoneNumber)
        return ResponseEntity.ok().body(
            GlobalResponse(
                GlobalStatus.SUCCESS.status,
                "Sent New Otp",
                "Set New Otp"
            )
        )
    }



}