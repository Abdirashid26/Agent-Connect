package com.faisaldev.wallet.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal


@Table(name = "tb_trx_message")
data class TrxMessage(

    @Id
    val id : Long? =null,
    @Column("debit_account")
    val debitAccount: String,
    @Column("credit_account")
    val creditAccount: String,
    @Column("narration")
    val narration : String,
    @Column("transaction_ref")
    val transactionRef : String,
    @Column("amount")
    val amount : BigDecimal,

    )