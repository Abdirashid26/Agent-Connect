package com.faisaldev.wallet.service.impl

import com.faisaldev.wallet.model.TrxMessage
import com.faisaldev.wallet.repository.TrxMessageRepository
import com.faisaldev.wallet.service.TrxMessagesService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service


@Service
class TrxMessageServiceImpl(
    private val trxMessageRepository: TrxMessageRepository
): TrxMessagesService {

    val serviceJob = CoroutineScope(SupervisorJob() + Dispatchers.IO)


    override fun saveTrxMessage(trxMessage: TrxMessage) {
        serviceJob.launch {
            try {
                trxMessageRepository.save(trxMessage)
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}