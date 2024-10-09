package com.faisaldev.wallet.service

import com.faisaldev.wallet.model.TrxMessage

interface TrxMessagesService {


    fun saveTrxMessage(trxMessage: TrxMessage)


}