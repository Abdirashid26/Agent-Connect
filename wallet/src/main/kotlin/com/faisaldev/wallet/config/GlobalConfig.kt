package com.faisaldev.wallet.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient


@Configuration
class GlobalConfig {



    @Bean
    fun produceWebClientBuilder(): WebClient.Builder {
        return WebClient.builder()
    }


}