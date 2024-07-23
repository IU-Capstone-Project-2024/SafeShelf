package com.techaas.clients

import com.techaas.dto.Dish
import com.techaas.dto.requests.GenerateDishRequest
import com.techaas.exceptions.GenerateDishException
import kotlinx.coroutines.reactor.awaitSingle
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.bodyToMono

@Service
@RequiredArgsConstructor
class GeneratorClient(
    private val generatorDishesClient: WebClient
) {
    suspend fun generateBreakfast(generateDishRequest: GenerateDishRequest): Dish =
        generatorDishesClient
            .post()
            .uri("/breakfast")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(generateDishRequest))
            .retrieve()
            .onStatus({ responseStatus ->
                responseStatus == HttpStatus.INTERNAL_SERVER_ERROR
            }) { throw GenerateDishException("ML server not found") }
            .bodyToMono<Dish>()
            .awaitSingle()


    suspend fun generateLunch(generateDishRequest: GenerateDishRequest) =
        generatorDishesClient
            .post()
            .uri("/lunch")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(generateDishRequest))
            .retrieve()
            .onStatus({ responseStatus ->
                responseStatus == HttpStatus.INTERNAL_SERVER_ERROR
            }) { throw GenerateDishException("ML server not found") }
            .bodyToMono<Dish>()
            .awaitSingle()

    suspend fun generateDinner(generateDishRequest: GenerateDishRequest) =
        generatorDishesClient
            .post()
            .uri("/dinner")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(generateDishRequest))
            .retrieve()
            .onStatus({ responseStatus ->
                responseStatus == HttpStatus.INTERNAL_SERVER_ERROR
            }) { throw GenerateDishException("ML server not found") }
            .bodyToMono<Dish>()
            .awaitSingle()

}