package com.techaas.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SafeShelfApplication

fun main(args: Array<String>) {
	runApplication<SafeShelfApplication>(*args)
}
