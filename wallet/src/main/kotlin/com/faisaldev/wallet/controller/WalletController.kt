package com.faisaldev.wallet.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/agent-connect/wallet-service/")
class WalletController {


    @PostMapping("test")
    suspend fun walletTest() : ResponseEntity<String>{
        return ResponseEntity.ok().body("Test")
    }


}