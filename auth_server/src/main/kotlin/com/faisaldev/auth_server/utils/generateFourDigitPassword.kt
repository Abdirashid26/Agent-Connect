package com.faisaldev.auth_server.utils

fun generateFourDigitPassword(): String {
    return (1000..9999).random().toString() // Generate a random number between 1000 and 9999
}
