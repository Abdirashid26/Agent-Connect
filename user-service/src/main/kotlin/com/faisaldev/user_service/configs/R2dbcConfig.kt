package com.faisaldev.user_service.configs

//import com.faisaldev.user_service.shard_configs.ShardRoutingConnectionFactory
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

//@Configuration
//@EnableR2dbcRepositories(basePackages = ["com.faisaldev.user_service.repositories"])
//class R2dbcConfig {
//
//    @Bean
//    fun shard1ConnectionFactory(): ConnectionFactory {
//        val options = ConnectionFactoryOptions.builder()
//            .option(ConnectionFactoryOptions.DRIVER, "postgresql")
//            .option(ConnectionFactoryOptions.HOST, "localhost")
//            .option(ConnectionFactoryOptions.PORT, 5432)
//            .option(ConnectionFactoryOptions.DATABASE, "USERS_AGENT_CONNECT")
//            .option(ConnectionFactoryOptions.USER, "postgres")
//            .option(ConnectionFactoryOptions.PASSWORD, "faisal26")
//            .build()
//
//        return ConnectionFactories.get(options)
//    }
//
//    @Bean
//    fun shard2ConnectionFactory(): ConnectionFactory {
//        val options = ConnectionFactoryOptions.builder()
//            .option(ConnectionFactoryOptions.DRIVER, "postgresql")
//            .option(ConnectionFactoryOptions.HOST, "localhost")
//            .option(ConnectionFactoryOptions.PORT, 5432)
//            .option(ConnectionFactoryOptions.DATABASE, "USERS_AGENT_CONNECT_1")
//            .option(ConnectionFactoryOptions.USER, "postgres")
//            .option(ConnectionFactoryOptions.PASSWORD, "faisal26")
//            .build()
//
//        return ConnectionFactories.get(options)
//    }
//
//
//
//}