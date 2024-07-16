package com.techaas.services

import com.techaas.data_entities.DishType
import com.techaas.domain.entity.DishesEntity
import com.techaas.domain.entity.UserEntity
import com.techaas.domain.jpa.JpaDishesService
import com.techaas.domain.jpa.JpaUserProductService
import com.techaas.domain.jpa.JpaUserService
import com.techaas.dto.IngredientsEntity
import com.techaas.dto.ProductToGenerator
import com.techaas.dto.requests.CookedDishRequest
import com.techaas.dto.requests.GenerateDishRequest
import com.techaas.dto.responses.UserDishesResponse
import com.techaas.tools.ConvertToGenerator
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Component
@RequiredArgsConstructor
class DishService(
    private val generateKpfcService: GenerateKpfcService,
    private val jpaUserService: JpaUserService,
    private val jpaUserProductService: JpaUserProductService,
    private val convertToGenerator: ConvertToGenerator,
    private val activityMap: Map<String, Double>,
    private val jpaDishesService: JpaDishesService
) {
    @Transactional
    fun generateRation(login: String): GenerateDishRequest {
        val user: UserEntity = jpaUserService.getUser(login)
        val productToGenerators: List<ProductToGenerator> =
            convertToGenerator.convert(jpaUserProductService.getProductsByUser(user))
        val kpfc = generateKpfcService.getKpfc(
            weight = user.weight.toDouble(),
            height = user.height.toDouble(),
            age = user.age,
            sex = user.sex,
            activity = activityMap.getValue(user.lifestyle),
            goal = user.goal
        )
        return GenerateDishRequest(
            kpfc = kpfc,
            productToGenerators = productToGenerators
        )
    }

    @Transactional
    fun getDishesForTheUser(login: String): List<UserDishesResponse> {
        val user: UserEntity = jpaUserService.getUser(login)
        val dishesResult: List<DishesEntity> = jpaDishesService.getDishesByUser(user.id)

        val result: MutableList<UserDishesResponse> = mutableListOf()
        for (dish in dishesResult) {
            result.add(
                UserDishesResponse(
                    id = dish.id!!,
                    name = dish.name,
                    ingredients = dish.ingredients,
                    description = dish.description,
                    type = dish.type
                )
            )
        }

        return result
    }


    @Transactional
    fun saveDish() {
        var hues: MutableList<IngredientsEntity> = mutableListOf()
        hues.add(IngredientsEntity(name = "Молочный петух", userProductId = 3, weight = 10.toBigDecimal()))
        hues.add(IngredientsEntity(name = "Курица без петуха", userProductId = 4, weight = 20.toBigDecimal()))
        jpaDishesService.saveDish("dadaa", hues, "123123", DishType.L, 2)
    }

    @Transactional
    fun cooked(request: CookedDishRequest) {
        val user: UserEntity = jpaUserService.getUser(request.login)
        val ingredients: List<IngredientsEntity> = jpaDishesService.findDishByID(request.id).ingredients
        for (ingredient in ingredients) {
            val currentWeight: BigDecimal =
                jpaUserProductService.getUserProductByID(ingredient.userProductId).weight - ingredient.weight
            if (currentWeight <= 0.toBigDecimal()) {
                jpaUserProductService.deleteUserProductEntityByID(ingredient.userProductId)
            } else {
                jpaUserProductService.updateProductWeight(ingredient.userProductId, currentWeight)
            }
        }
        jpaDishesService.deleteDish(request.id)
    }
}