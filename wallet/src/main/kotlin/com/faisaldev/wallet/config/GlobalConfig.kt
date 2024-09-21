package com.faisaldev.wallet.config

import com.google.gson.Gson
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient


@Configuration
class GlobalConfig {



    @Bean
    fun produceWebClientBuilder(): WebClient.Builder {
        return WebClient.builder()
    }

    @Bean
    fun gson(): Gson {
        return Gson() // You can customize Gson with settings if needed
    }


}