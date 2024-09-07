package com.faisaldev.user_service.configs

import com.google.gson.Gson
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class CommonConfigs {


    @Bean
    fun getGson(): Gson {
        return Gson()
    }


}