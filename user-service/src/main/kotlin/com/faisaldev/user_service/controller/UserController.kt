package com.faisaldev.user_service.controller

import com.faisaldev.user_service.models.UserDto
import com.faisaldev.user_service.utils.GlobalResponse
import com.faisaldev.user_service.utils.GlobalStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/agent-connect/user-service")
class UserController {



    @PostMapping("/createUserAccount", consumes = ["multipart/form-data"])
    fun createUserAccount(
        @RequestPart("data") userDto: UserDto, // JSON object
        @RequestPart("files") images: List<MultipartFile> // List of images
    ): ResponseEntity<GlobalResponse<UserDto>> {
        // Process userDto and files here
        // For example, save files to storage and create user account with userDto

        // Assuming you have a service to handle user creation and file processing
        // val savedUser = userService.createUserAccount(userDto, files)

        // Return response
        return ResponseEntity.ok(GlobalResponse(GlobalStatus.SUCCESS,"Success",userDto)) // Adjust based on your GlobalResponse implementation
    }


}