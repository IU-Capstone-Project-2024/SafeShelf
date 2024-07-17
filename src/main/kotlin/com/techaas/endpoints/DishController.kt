package com.techaas.endpoints

import com.techaas.clients.GeneratorClient
import com.techaas.domain.entity.DishesEntity
import com.techaas.dto.requests.CookedDishRequest
import com.techaas.dto.requests.GenerateDishRequest
import com.techaas.dto.responses.UserDishesResponse
import com.techaas.services.DishService
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
class DishController(
    private val dishService: DishService,
    private val generatorClient: GeneratorClient
) {
    @PostMapping("/breakfast/{login}")
    fun generateBreakfast(@PathVariable(name = "login") login: String) {
        val productToGenerator = dishService.generateRation(login)
        //return generatorClient.generateBreakfast(productToGenerator).block()
    }


    @PostMapping("/lunch/{login}")
    fun generateLunch(@PathVariable(name = "login") login: String) {
        dishService.saveDish()
    }

    @PostMapping("/dinner/{login}")
    fun generateDinner(@PathVariable(name = "login") login: String) {

    }

    @GetMapping("/{login}")
    fun getDishes(@PathVariable(name = "login") login: String): List<UserDishesResponse> {
        val result = dishService.getDishesForTheUser(login)
        return result
    }

    @DeleteMapping("/cooked")
    fun deleteCooked(@RequestBody cookedDishRequest: CookedDishRequest) {
        dishService.cooked(cookedDishRequest)
    }
}