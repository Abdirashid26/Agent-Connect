package com.faisaldev.user_service.utils

data class GlobalResponse<T>(
    var status : String,
    var message : String,
    var data : T?
)