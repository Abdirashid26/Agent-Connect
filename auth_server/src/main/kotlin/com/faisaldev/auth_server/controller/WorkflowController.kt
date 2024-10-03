package com.faisaldev.auth_server.controller

import com.faisaldev.auth_server.dtos.ApproveWorkflowStep
import com.faisaldev.auth_server.kafka.PublishingServiceImpl
import com.faisaldev.user_service.utils.GlobalResponse
import com.faisaldev.user_service.utils.GlobalStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono


@RestController
    @RequestMapping("/api/v1/agent-connect/auth-service/workflow")
class WorkflowController(
    private val publishingServiceImpl: PublishingServiceImpl
){




    @PostMapping("/update-step-status")
    suspend fun updateWorkflowStepStatus(
        @RequestBody approveWorkflowStep: ApproveWorkflowStep,
        @ModelAttribute("profile") profile: String?
    ): ResponseEntity<GlobalResponse<String>> {
        approveWorkflowStep.approverType = profile ?: ""
        println("Approval Workflow Step : " + approveWorkflowStep)
        publishingServiceImpl.sendWorkflowStepStatus(approveWorkflowStep)
        return ResponseEntity.ok(
            GlobalResponse(
                GlobalStatus.SUCCESS.status,
                "Workflow Status approval request has been received",
                ""
            )
        )
    }



}