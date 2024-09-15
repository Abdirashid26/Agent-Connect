package com.faisaldev.wallet.controller

import com.faisaldev.user_service.utils.GlobalResponse
import com.faisaldev.user_service.utils.GlobalStatus
import com.faisaldev.wallet.dto.GetWalletAccountsDto
import com.faisaldev.wallet.model.Wallet
import com.faisaldev.wallet.service.WalletService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/agent-connect/wallet-service/")
class WalletController(
    val walletService: WalletService,
){


    @PostMapping("test")
    suspend fun walletTest() : ResponseEntity<String>{
        return ResponseEntity.ok().body("Test")
    }


    @PostMapping("/wallet-accounts")
    suspend fun getWalletAccounts(
        @RequestBody getWalletAccountsDto: GetWalletAccountsDto
    ) : ResponseEntity<GlobalResponse<List<Wallet>>>{
        val allWalletAccounts = walletService.getAllWallets(getWalletAccountsDto)
        return if (allWalletAccounts != null){
            ResponseEntity.ok().body(
                GlobalResponse(GlobalStatus.SUCCESS.status,"All Wallet Accounts",allWalletAccounts)
            )
        }else{
            ResponseEntity.ok().body(
                GlobalResponse(GlobalStatus.FAILURE.status,"No Wallet Accounts Found",allWalletAccounts)
            )
        }
    }


}