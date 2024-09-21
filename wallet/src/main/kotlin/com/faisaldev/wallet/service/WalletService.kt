package com.faisaldev.wallet.service

import com.faisaldev.user_service.utils.GlobalResponse
import com.faisaldev.wallet.dto.BalanceInquiryRequest
import com.faisaldev.wallet.dto.BalanceInquiryResponse
import com.faisaldev.wallet.dto.GetWalletAccountsDto
import com.faisaldev.wallet.model.Wallet

interface WalletService {

    suspend fun performBalanceInquiry(balanceInquiryRequest: BalanceInquiryRequest) : GlobalResponse<BalanceInquiryResponse>

    suspend fun getAllWallets(getWalletAccountsDto: GetWalletAccountsDto): List<Wallet>?

}