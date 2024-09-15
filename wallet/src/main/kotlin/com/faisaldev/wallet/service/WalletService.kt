package com.faisaldev.wallet.service

import com.faisaldev.wallet.dto.GetWalletAccountsDto
import com.faisaldev.wallet.model.Wallet

interface WalletService {

    suspend fun getAllWallets(getWalletAccountsDto: GetWalletAccountsDto): List<Wallet>?

}