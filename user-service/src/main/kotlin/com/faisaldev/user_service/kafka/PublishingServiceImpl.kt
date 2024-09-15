package com.faisaldev.user_service.kafka

import com.faisaldev.user_service.models.UserDto
import com.faisaldev.user_service.utils.KafkaTopics
import com.google.gson.Gson
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.util.Map


@Service
class PublishingServiceImpl(
    private val streamBridge : StreamBridge,
    private val gson : Gson
){


    fun createUserCredentials(userDto: UserDto) {
        Mono.fromRunnable<Any> {
            streamBridge.send(KafkaTopics.CREATE_AUTH_CREDENTIALS, gson.toJson(userDto))
            streamBridge.send(KafkaTopics.CREATE_CUSTOMER_WALLET, gson.toJson(userDto))
        }.publishOn(Schedulers.boundedElastic()).subscribeOn(Schedulers.boundedElastic()).subscribe()
    }


}