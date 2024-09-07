package com.faisaldev.user_service

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<UserServiceApplication>().with(TestcontainersConfiguration::class).run(*args)
}
