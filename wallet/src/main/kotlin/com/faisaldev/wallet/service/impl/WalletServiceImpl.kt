package com.faisaldev.wallet.service.impl

import com.faisaldev.wallet.dto.GetWalletAccountsDto
import com.faisaldev.wallet.model.Wallet
import com.faisaldev.wallet.repository.WalletRepository
import com.faisaldev.wallet.service.WalletService
import org.springframework.stereotype.Service


@Service
class WalletServiceImpl(
    private val walletRepository: WalletRepository,
): WalletService {


    override suspend fun getAllWallets(getWalletAccountsDto: GetWalletAccountsDto): List<Wallet>? {
        val walletAccounts = walletRepository.findAllByPhoneNumber(getWalletAccountsDto.username)
        return walletAccounts
    }



}