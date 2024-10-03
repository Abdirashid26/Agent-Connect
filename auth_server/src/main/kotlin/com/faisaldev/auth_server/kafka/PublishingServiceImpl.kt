package com.faisaldev.auth_server.kafka

import com.faisaldev.auth_server.dtos.ApproveWorkflowStep
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.stereotype.Service
import kotlin.coroutines.CoroutineContext


@Service
class PublishingServiceImpl(
    private val streamBridge: StreamBridge,
    private val gson: Gson,
){




    fun sendWorkflowStepStatus(approveWorkflowStep: ApproveWorkflowStep) {
        streamBridge.send(
            "fa-workflow-engine-topic",
            gson.toJson(approveWorkflowStep)
        )
    }






}