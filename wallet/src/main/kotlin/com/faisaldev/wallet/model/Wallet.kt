package com.faisaldev.wallet.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDateTime





@Table(name = "tb_accounts")
data class Wallet(

    // Primary key: User's phone number
    @Id
    val phoneNumber: String,

    // Unique account number for the wallet
    val accountNumber: String,

    // Current total balance including funds on hold
    var actualBalance: BigDecimal = BigDecimal.ZERO,

    // Available balance for transactions (actual balance minus holds)
    var availableBalance: BigDecimal = BigDecimal.ZERO,

    // Maximum allowable credit for the wallet
    var allowedCredit: BigDecimal = BigDecimal.ZERO,

    // Maximum allowable debit (daily or per transaction)
    var allowedDebit: BigDecimal = BigDecimal.ZERO,

    // Holds the currency in which the wallet operates, e.g., KES for Kenyan Shillings
    var currency: String = "KES",

    // Status of the wallet (e.g., ACTIVE, SUSPENDED, CLOSED, FROZEN)
    var status: String = "ACTIVE",

    // Indicates if the wallet is locked (for security reasons or compliance)
    var isLocked: Boolean = false,

    // Indicates if the wallet is frozen (for suspicious activity, etc.)
    var isFrozen: Boolean = false,

    // A field to track any fees associated with the wallet account
    var fee: BigDecimal = BigDecimal.ZERO,

    // Optional overdraft balance (if applicable)
    var overdraftLimit: BigDecimal = BigDecimal.ZERO,

    // Date when the wallet was created
    val createdAt: LocalDateTime = LocalDateTime.now(),

    // Last updated date for the wallet
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    // Wallet expiry date (if applicable, e.g., for prepaid wallets)
    var expiryDate: LocalDateTime? = null,

    // Whether the wallet is currently active or not
    var isActive: Boolean = true,

    // Identifier for any linked external services or accounts (optional)
    var externalAccountId: String? = null,

    // Wallet type (e.g., PERSONAL, BUSINESS)
    var walletType: String = "PERSONAL",

    // Last transaction timestamp
    var lastTransactionAt: LocalDateTime? = null,

    // Field for compliance requirements (e.g., AML status)
    var complianceStatus: String = "COMPLIANT",

    // Optional note for the wallet (e.g., for internal use or user instructions)
    var notes: String? = null
)
