package com.techaas.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties(prefix = "clients", ignoreInvalidFields = false)
data class ClientConfig(
    @get:Bean
    val generatorDishes: GeneratorDishes
) {
    data class GeneratorDishes(val baseUrl: String)
}