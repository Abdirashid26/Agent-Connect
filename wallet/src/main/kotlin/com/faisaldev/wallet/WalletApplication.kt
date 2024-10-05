package com.faisaldev.wallet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableTransactionManagement
@SpringBootApplication
class WalletApplication

fun main(args: Array<String>) {
	runApplication<WalletApplication>(*args)
}
