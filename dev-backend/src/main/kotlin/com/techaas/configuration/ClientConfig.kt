package com.techaas.configuration

import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.validation.annotation.Validated
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration

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
            .clientConnector(
                ReactorClientHttpConnector(
                    HttpClient.create().responseTimeout(Duration.ofSeconds(300))
                )
            )
            .baseUrl(generatorDishes.baseUrl)
            .build()
    }

    data class GeneratorDishes(val baseUrl: String)
}