package com.faisaldev.wallet.service.impl

import com.faisaldev.user_service.utils.GlobalResponse
import com.faisaldev.user_service.utils.GlobalStatus
import com.faisaldev.wallet.dto.*
import com.faisaldev.wallet.model.TrxMessage
import com.faisaldev.wallet.model.Wallet
import com.faisaldev.wallet.repository.WalletRepository
import com.faisaldev.wallet.service.AuthService
import com.faisaldev.wallet.service.TrxMessagesService
import com.faisaldev.wallet.service.WalletService
import com.faisaldev.wallet.utils.UniqueIdGeneratorBitManipulation
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class WalletServiceImpl(
    private val walletRepository: WalletRepository,
    private val authService: AuthService,
    private val trxMessagesService: TrxMessagesService
): WalletService {


    val uniqueIdGenerator = UniqueIdGeneratorBitManipulation(1, 1)



    override suspend fun performBalanceInquiry(balanceInquiryRequest: BalanceInquiryRequest): GlobalResponse<BalanceInquiryResponse> {
        // Directly call the suspend function
        val validationResponse = authService.validatePin(ValidatePinDto(balanceInquiryRequest.phoneNumber, balanceInquiryRequest.transactionPin))

        // Proceed with  balance inquiry logic after PIN validation
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

    @Transactional
    override suspend fun performFundsTransfer(fundsTransferRequest: FundsTransferRequest): GlobalResponse<FundsTransferResponse> {
        //validate the pin first
        val validationResponse = authService.validatePin(ValidatePinDto(fundsTransferRequest.phoneNumber, fundsTransferRequest.transactionPin))

        // Proceed with funds transfer logic after PIN validation
        if (validationResponse.status == GlobalStatus.FAILURE.status) {
            return GlobalResponse(
                GlobalStatus.FAILURE.status,
                "PIN validation failed",
                null
            )
        }



        // check if the debit account exists
        val wallet  = walletRepository.findByPhoneNumberAndAccountNumber(fundsTransferRequest.phoneNumber, fundsTransferRequest.debitAccountNumber);

        if (wallet == null) {
            return GlobalResponse(
                GlobalStatus.FAILURE.status,
                "Debit Account not found !",
                null
            )
        }

        // else if wallet is present then proceed to check if the balance is possible
        if(fundsTransferRequest.amount.toBigDecimal() > wallet.availableBalance){
            return GlobalResponse(
                GlobalStatus.FAILURE.status,
                "Sorry, but you have insufficient balance",
                null
            )
        }

        // else proceed to check if the credit account exists
        val creditWallet = walletRepository.findByAccountNumber(fundsTransferRequest.creditAccountNumber)

        if (creditWallet == null){
            return GlobalResponse(
                GlobalStatus.FAILURE.status,
                "Credit Account not found !",
                null
            )
        }

        // if present transfer funds from  the debit account
        wallet.availableBalance -= fundsTransferRequest.amount.toBigDecimal()
        wallet.actualBalance -= fundsTransferRequest.amount.toBigDecimal()


        // and then credit the Credit account
        creditWallet.availableBalance += fundsTransferRequest.amount.toBigDecimal()
        creditWallet.actualBalance += fundsTransferRequest.amount.toBigDecimal()


        // save the new wallet details for the debit wallet and credit wallet
        walletRepository.save(wallet)
        walletRepository.save(creditWallet)

        val transactionRef = uniqueIdGenerator.generateUniqueId()
        val trxMessage = TrxMessage(
            debitAccount = wallet.accountNumber,
            creditAccount = creditWallet.accountNumber,
            narration = "Funds Transfer",
            transactionRef = transactionRef.toString(),
            amount = fundsTransferRequest.amount.toBigDecimal(),
        )

        trxMessagesService.saveTrxMessage(trxMessage)

        val fundsTransferResponse = FundsTransferResponse(transactionRef = transactionRef.toString())

        
        return GlobalResponse(
            GlobalStatus.SUCCESS.status,
            "Funds Transfer Successful",
            fundsTransferResponse
        )


    }


    override suspend fun getAllWallets(getWalletAccountsDto: GetWalletAccountsDto): List<Wallet>? {
        val walletAccounts = walletRepository.findAllByPhoneNumber(getWalletAccountsDto.username)
        return walletAccounts
    }



}