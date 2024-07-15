package com.techaas

import com.techaas.configuration.ClientConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ClientConfig::class)
class SafeShelfApplication
fun main(args: Array<String>) {
	runApplication<SafeShelfApplication>(*args)
}
