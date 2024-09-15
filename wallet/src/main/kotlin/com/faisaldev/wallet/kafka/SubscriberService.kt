package com.faisaldev.wallet.kafka

import com.faisaldev.wallet.dto.UserDto
import com.faisaldev.wallet.dto.createCustomerWallet
import com.faisaldev.wallet.repository.WalletRepository
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service


@Service
class SubscriberService(
    private val walletRepository: WalletRepository
){

    private val gson = Gson()
    private val scope = CoroutineScope(Dispatchers.IO)


    @Bean
    fun createCustomerWalletAccount(): (String) -> Unit = { message ->
        println("Received message: $message")
        // Launch a coroutine in a non-blocking way
        scope.launch {
            val userDto: UserDto = gson.fromJson(message, UserDto::class.java)
            val customerWallet = userDto.createCustomerWallet()
            walletRepository.save(customerWallet)
        }
    }


}