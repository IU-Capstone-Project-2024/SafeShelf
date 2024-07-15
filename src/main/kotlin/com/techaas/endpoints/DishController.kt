package com.techaas.endpoints

import com.techaas.clients.GeneratorClient
import com.techaas.dto.requests.GenerateDishRequest
import com.techaas.services.DishService
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
class DishController(
    private val dishService: DishService,
    private val generatorClient: GeneratorClient
) {
    @PostMapping("/breakfast/{login}")
    fun generateBreakfast(@PathVariable(name = "login") login: String): GenerateDishRequest =
        dishService.generateRation(login)


    @PostMapping("/lunch/{login}")
    fun generateLunch(@PathVariable(name = "login") login: String) {

    }

    @PostMapping("/dinner/{login}")
    fun generateDinner(@PathVariable(name = "login") login: String) {

    }
}