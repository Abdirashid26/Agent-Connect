package com.faisaldev.wallet.dto

import jakarta.validation.constraints.NotNull

data class BalanceInquiryRequest(
    @NotNull
    val phoneNumber : String,
    @NotNull
    val transactionPin : String
)


data class BalanceInquiryResponse(
    var accountNumber: String? = null,
    var availableBalance : String? = null,
    var actualBalance : String? = null
)