package com.techaas.configuration

import jakarta.annotation.PostConstruct
import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.validation.annotation.Validated
import org.springframework.web.reactive.function.client.WebClient

@Validated
@ConfigurationProperties(prefix = "clients", ignoreInvalidFields = false)
data class ClientConfig(
    @get:Bean
    @NotNull
    val generatorDishes: GeneratorDishes
) {
    @Bean
    fun generatorDishesClient(): WebClient {
        return WebClient
            .builder()
            .baseUrl(generatorDishes.baseUrl)
            .build()
    }

    @PostConstruct
    fun check() {
        println(generatorDishes.baseUrl)
    }

    data class GeneratorDishes(val baseUrl: String)
}