package com.techaas.clients

import com.techaas.dto.ProductToGenerator
import lombok.RequiredArgsConstructor
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import reactor.core.publisher.Mono

@Service
@RequiredArgsConstructor
class GeneratorClient(
    private val generatorDishesClient: WebClient
) {
    fun generateBreakfast(productToGenerator: ProductToGenerator) {
//        return generatorDishesClient
//            .post()
//            .uri("/breakfast")
//            .body(BodyInserters.)
//            .accept(MediaType.APPLICATION_JSON)
    }
}