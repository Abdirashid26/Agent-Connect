package com.faisaldev.wallet.service.impl

import com.faisaldev.user_service.utils.GlobalResponse
import com.faisaldev.user_service.utils.GlobalStatus
import com.faisaldev.wallet.dto.BalanceInquiryRequest
import com.faisaldev.wallet.dto.BalanceInquiryResponse
import com.faisaldev.wallet.dto.GetWalletAccountsDto
import com.faisaldev.wallet.dto.ValidatePinDto
import com.faisaldev.wallet.model.Wallet
import com.faisaldev.wallet.repository.WalletRepository
import com.faisaldev.wallet.service.AuthService
import com.faisaldev.wallet.service.WalletService
import org.springframework.stereotype.Service


@Service
class WalletServiceImpl(
    private val walletRepository: WalletRepository,
    private val authService: AuthService
): WalletService {


    override suspend fun performBalanceInquiry(balanceInquiryRequest: BalanceInquiryRequest): GlobalResponse<BalanceInquiryResponse> {
        // Directly call the suspend function
        val validationResponse = authService.validatePin(ValidatePinDto(balanceInquiryRequest.phoneNumber, balanceInquiryRequest.transactionPin))

        // Proceed with your balance inquiry logic after PIN validation
        if (validationResponse.status == GlobalStatus.FAILURE.status) {
            return GlobalResponse(
                GlobalStatus.FAILURE.status,
                "PIN validation failed",
                null
            )
        }

        // Proceed with balance inquiry if PIN validation succeeds
        val wallet = walletRepository.findByPhoneNumber(balanceInquiryRequest.phoneNumber)
        if (wallet == null) {
            return GlobalResponse(GlobalStatus.FAILURE.status, "Wallet not found", null)
        }

        val availableBalance = wallet.availableBalance
        val actualBalance = wallet.actualBalance

        val balanceInquiryResponse = BalanceInquiryResponse(
            actualBalance = actualBalance.toString(),
            availableBalance = availableBalance.toString(),
            accountNumber = wallet.accountNumber
        )

        return GlobalResponse(GlobalStatus.SUCCESS.status, "Wallet Balance", balanceInquiryResponse)
    }



    override suspend fun getAllWallets(getWalletAccountsDto: GetWalletAccountsDto): List<Wallet>? {
        val walletAccounts = walletRepository.findAllByPhoneNumber(getWalletAccountsDto.username)
        return walletAccounts
    }



}