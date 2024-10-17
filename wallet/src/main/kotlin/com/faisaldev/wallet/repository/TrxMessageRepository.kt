package com.faisaldev.wallet.repository

import com.faisaldev.wallet.model.TrxMessage
import com.faisaldev.wallet.model.Wallet
import org.hibernate.validator.constraints.CreditCardNumber
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TrxMessageRepository  : CoroutineCrudRepository<TrxMessage, Long> {

    suspend fun findAllByCreditAccountOrDebitAccount(creditAccount: String, debitAccount : String) : List<TrxMessage>

}