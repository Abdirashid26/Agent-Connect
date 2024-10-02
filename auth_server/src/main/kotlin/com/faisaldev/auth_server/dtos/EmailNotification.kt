package com.faisaldev.auth_server.dtos;




data class EmailNotification(
    var title: String = "",
    var message: String = "",
    var email: String = "",
    var type : String = "html",
)

