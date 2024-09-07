package com.faisaldev.user_service.services.impl

import com.faisaldev.user_service.error_handlers.GlobalException
import com.faisaldev.user_service.kafka.PublishingServiceImpl
import com.faisaldev.user_service.models.User
import com.faisaldev.user_service.models.UserDto
import com.faisaldev.user_service.models.toDto
import com.faisaldev.user_service.repositories.UsersRepository
import com.faisaldev.user_service.services.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Flux
import java.nio.file.Path
import java.nio.file.Paths


@Service
class UserServiceImpl(
    private val  usersRepository: UsersRepository,
    private val publishingServiceImpl: PublishingServiceImpl
): UserService {

    private final val  basePath : Path = Paths.get("src/main/resources/upload/")

    override suspend fun createUser(userDto: UserDto, idImages: Flux<FilePart>): User? {

        if (usersRepository.findByPhoneNumber(userDto.phoneNumber) != null){
            throw GlobalException("Username already exists")
        }

        val transferredFiles = idImages
            .flatMap { filePart ->
                val filePath = basePath.resolve(userDto.idNumber + "_" + filePart.filename())
                filePart.transferTo(filePath).thenReturn(filePath)
            }
            .collectList()
            .awaitSingle()

        if (transferredFiles.isEmpty()) {
            throw GlobalException("No files were processed.")
        }

        val idFrontImage = transferredFiles.find { it.fileName.toString().contains("front") }?.toString() ?: ""
        val idBackImage = transferredFiles.find { it.fileName.toString().contains("back") }?.toString() ?: ""

        val user = User(
            firstName = userDto.firstName,
            lastName = userDto.lastName,
            phoneNumber = userDto.phoneNumber,
            county = userDto.county,
            estate = userDto.estate,
            idNumber = userDto.idNumber,
            idFrontImage = idFrontImage,
            idBackImage = idBackImage
        )

        return usersRepository.save(user)
    }

    override suspend fun activateUserAccount(phoneNumber: String): User? {
        val user = usersRepository.findByPhoneNumber(phoneNumber)
        if (user == null){
            println("Failed to Activate User Account because it came back null")
            return null
        }else{
            user.active = true
            val userSaved = usersRepository.save(user)
            userSaved.let {
                 // Send User Dto Auth Service to create Credentials
                publishingServiceImpl.createUserCredentials(userSaved.toDto())
            }
            return userSaved
        }
    }


}