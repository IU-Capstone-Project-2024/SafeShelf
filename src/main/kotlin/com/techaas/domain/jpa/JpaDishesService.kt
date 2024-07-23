package com.techaas.domain.jpa

import com.techaas.data_entities.DishType
import com.techaas.domain.entity.DishesEntity
import com.techaas.domain.entity.IngredientEntity
import com.techaas.domain.jpa.bases_quieries.BaseDishesRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class JpaDishesService(
    private val baseDishesRepository: BaseDishesRepository
) {
    fun saveDish(name: String, ingredients: List<IngredientEntity>, description: String, type: DishType, userId: Long) {
        val dish = DishesEntity(
            name = name,
            ingredients = ingredients,
            description = description,
            type = type,
            userId = userId
        )
        baseDishesRepository.save(dish)
    }

    fun getDishesByUser(userId: Long): List<DishesEntity> = baseDishesRepository.findDishesEntityByUserId(userId)

    fun deleteDish(id: String) = baseDishesRepository.deleteDishesEntityById(id)

    fun deleteDishByUserIdAndDishType(userId: Long, type: DishType) =
        baseDishesRepository.deleteDishesEntityByUserIdAndType(userId, type)

    fun findDishByID(id: String): DishesEntity = baseDishesRepository.findDishesEntityById(id)

}
