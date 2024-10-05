package com.faisaldev.wallet.repository

import com.faisaldev.wallet.model.Wallet
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface WalletRepository : CoroutineCrudRepository<Wallet, Long> {

    suspend fun findAllByPhoneNumber(phone: String): List<Wallet>?
    suspend fun findByPhoneNumber(phone: String): Wallet?

    suspend fun findByPhoneNumberAndAccountNumber(phone: String, account: String): Wallet?

    suspend fun findByAccountNumber(account: String): Wallet?

}