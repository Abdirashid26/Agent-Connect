package com.faisaldev.auth_server.dtos;

import com.faisaldev.auth_server.enums.WorkflowStatus;


data class ApproveWorkflowStep (


    val workflowItemId : String,
    val workflowStatus : WorkflowStatus,
    var approverType : String = ""


)