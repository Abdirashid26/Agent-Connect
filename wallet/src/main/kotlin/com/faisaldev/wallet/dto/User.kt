package com.faisaldev.wallet.dto

import com.faisaldev.wallet.model.Wallet
import com.faisaldev.wallet.model.WalletAccountTypes
import com.faisaldev.wallet.model.WalletStatus
import java.math.BigDecimal
import java.time.LocalDateTime


data class UserDto(
    val firstName : String,
    val lastName : String,
    val phoneNumber: String,
    val county : String,
    val idNumber : String,
    val estate : String,
    val password : String?
)


fun UserDto.createCustomerWallet(): Wallet {

    // Generate the last 12 digits from the current timestamp
    val timestampPart = System.currentTimeMillis().toString().takeLast(12)
// Create the account number starting with 2460 followed by the 12-digit timestamp part
    val accountNumber = "2460$timestampPart"


    val wallet = Wallet(
        phoneNumber = this.phoneNumber,
        accountNumber = accountNumber,
        walletType = WalletAccountTypes.CUSTOMER.name,
        fee = BigDecimal(0),
        currency = "KES", // Default currency
        status = WalletStatus.ACTIVE.name, // Default status for new wallets
        isLocked = false, // Default unlocked state
        isFrozen = false, // Default unfrozen state
        createdAt = LocalDateTime.now(), // Set the creation date
        updatedAt = LocalDateTime.now(), // Set the updated date (same as created)
        isActive = true, // Mark the wallet as active
        complianceStatus = "COMPLIANT" // Default compliance status
    )

    return wallet
}
