package com.faisaldev.wallet.service

import com.faisaldev.user_service.utils.GlobalResponse
import com.faisaldev.wallet.dto.*
import com.faisaldev.wallet.model.Wallet

interface WalletService {

    suspend fun performBalanceInquiry(balanceInquiryRequest: BalanceInquiryRequest) : GlobalResponse<BalanceInquiryResponse>

    suspend fun performFundsTransfer(fundsTransferRequest: FundsTransferRequest ) : GlobalResponse<FundsTransferResponse>

    suspend fun getAllWallets(getWalletAccountsDto: GetWalletAccountsDto): List<Wallet>?

}