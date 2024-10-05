package com.faisaldev.wallet.dto

import jakarta.validation.constraints.NotNull


data class FundsTransferRequest(
    @NotNull
    val phoneNumber : String,

    @NotNull
    val debitAccountNumber: String,

    @NotNull
    val creditAccountNumber: String,

    @NotNull
    val amount: Double,


    @NotNull
    val transactionPin : String
)


data class FundsTransferResponse(
    var accountNumber: String? = null,
    var availableBalance : String? = null,
    var actualBalance : String? = null
)