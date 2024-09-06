package com.faisaldev.user_service.utils


enum class GlobalStatus(val status: String) {
    SUCCESS("00"),
    FAILURE("01");

    companion object {
        fun fromStatus(status: String): GlobalStatus? {
            return values().find { it.status == status }
        }
    }
}
