package com.techaas.endpoints

import com.techaas.clients.GeneratorClient
import com.techaas.data_entities.DishType
import com.techaas.dto.Dish
import com.techaas.dto.requests.CookedDishRequest
import com.techaas.dto.responses.UserDishesResponse
import com.techaas.services.DishService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
    suspend fun generateBreakfast(@PathVariable(name = "login") login: String) {
        println("Breakfast")
        val productToGenerator = withContext(Dispatchers.IO) {
            dishService.generateRation(login)
        }
        val breakfast: Dish = generatorClient.generateBreakfast(productToGenerator)
        print(breakfast)
        withContext(Dispatchers.IO) {
            dishService.saveDish(login, breakfast, DishType.B)
        }
    }


    @PostMapping("/lunch/{login}")
    suspend fun generateLunch(@PathVariable(name = "login") login: String) {
        println("Lunch")
        val productToGenerator = withContext(Dispatchers.IO) {
            dishService.generateRation(login)
        }
        val lunch: Dish = generatorClient.generateLunch(productToGenerator)
        print(lunch)
        withContext(Dispatchers.IO) {
            dishService.saveDish(login, lunch, DishType.L)
        }
    }

    @PostMapping("/dinner/{login}")
    suspend fun generateDinner(@PathVariable(name = "login") login: String) {
        println("Dinner")
        val productToGenerator = withContext(Dispatchers.IO) {
            dishService.generateRation(login)
        }
        val dinner: Dish = generatorClient.generateDinner(productToGenerator)
        print(dinner)
        withContext(Dispatchers.IO) {
            dishService.saveDish(login, dinner, DishType.D)
        }

    }

    @GetMapping("/{login}")
    suspend fun getDishes(@PathVariable(name = "login") login: String): List<UserDishesResponse> {
        val result = withContext(Dispatchers.IO) {
            dishService.getDishesForTheUser(login)
        }
        return result
    }

    @DeleteMapping("/breakfast/{login}")
    suspend fun deleteBreakfast(@PathVariable(name = "login") login: String) {
        println("Deleting breakfast")
        withContext(Dispatchers.IO) {
            dishService.deleteBreakfast(login)
        }
    }

    @DeleteMapping("/lunch/{login}")
    suspend fun deleteLunch(@PathVariable(name = "login") login: String) =
        withContext(Dispatchers.IO) {
            dishService.deleteLunch(login)
        }

    @DeleteMapping("/dinner/{login}")
    suspend fun deleteDinner(@PathVariable(name = "login") login: String) =
        withContext(Dispatchers.IO) {
            dishService.deleteDinner(login)
        }

    @DeleteMapping("/cooked")
    suspend fun deleteCooked(@RequestBody cookedDishRequest: CookedDishRequest) {
        withContext(Dispatchers.IO) {
            dishService.cooked(cookedDishRequest)
        }
    }
}