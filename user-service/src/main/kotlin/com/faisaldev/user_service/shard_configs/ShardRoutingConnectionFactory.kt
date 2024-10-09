package com.faisaldev.user_service.shard_configs

import io.r2dbc.spi.ConnectionFactory
import org.springframework.r2dbc.connection.lookup.AbstractRoutingConnectionFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

//@Component
//class ShardRoutingConnectionFactory(
//    private val shard1ConnectionFactory: ConnectionFactory,
//    private val shard2ConnectionFactory: ConnectionFactory
//) : AbstractRoutingConnectionFactory() {
//
//    override fun determineCurrentLookupKey(): Mono<Any> {
//        val phoneNumber = ShardContextHolder.currentCustomerFirstName
//        return determineShard(phoneNumber)
//    }
//
//    override fun determineTargetConnectionFactory(): Mono<ConnectionFactory> {
//        return determineCurrentLookupKey().flatMap { shardKey ->
//            val shardKeyString = shardKey as String // Cast the result to String
//            when (shardKeyString) {
//                "shard1" -> Mono.just(shard1ConnectionFactory)
//                "shard2" -> Mono.just(shard2ConnectionFactory)
//                else -> Mono.error(IllegalArgumentException("Unknown shard"))
//            }
//        }
//    }
//
//    private fun determineShard(customerFirstName: String?): Mono<Any> {
//        return if (customerFirstName?.get(0).toString().matches(Regex("^[A-M].*"))) {
//            Mono.just("shard1") // Letters A-M go to shard1
//        } else {
//            Mono.just("shard2") // Letters N-Z go to shard2
//        }
//    }
//
//}