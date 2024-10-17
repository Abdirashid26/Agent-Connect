package com.faisaldev.wallet.dto

import java.math.BigDecimal


data class StatementRequest(
    val accountNumber : String,
    val phoneNumber : String,
    val limit : Int = 5,
    val transactionPin : String
)


/**
 * Response will be list of this statement object
 */
data class StatementObject(
    val creditAccountNumber: String,
    val debitAccountNumber: String,
    val narration : String,
    val amount : BigDecimal,
    val transactionRef : String,
    val isCredit : Boolean,
)