package com.techaas.clients

import com.techaas.dto.requests.GenerateDishRequest
import lombok.RequiredArgsConstructor
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Service
@RequiredArgsConstructor
class GeneratorClient(
    private val generatorDishesClient: WebClient
) {
    suspend fun generateBreakfast(generateDishRequest: GenerateDishRequest): Mono<GenerateDishRequest> {
        return generatorDishesClient
            .post()
            .uri("/breakfast")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(generateDishRequest))
            .retrieve()
            .bodyToMono(GenerateDishRequest::class.java)

    }
}