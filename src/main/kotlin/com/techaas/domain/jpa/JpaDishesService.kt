package com.techaas.domain.jpa

import com.techaas.data_entities.DishType
import com.techaas.domain.entity.DishesEntity
import com.techaas.domain.entity.ProductEntity
import com.techaas.domain.jpa.bases_quieries.BaseDishesRepository
import com.techaas.domain.jpa.bases_quieries.BaseProductRepository
import com.techaas.dto.Dish
import com.techaas.dto.IngredientsEntity
import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Description
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
@RequiredArgsConstructor
class JpaDishesService(
    private val baseDishesRepository: BaseDishesRepository
) {
    fun saveDish(name: String, ingredients: List<IngredientsEntity>, description: String, type: DishType, userId: Long) {
        val dish = DishesEntity(
            name = name,
            ingredients = ingredients,
            description = description,
            type = type,
            userId = userId
        )
        baseDishesRepository.save(dish)
    }

    fun getDishesByUser(userId: Long): List<DishesEntity> {
        return baseDishesRepository.findDishesEntityByUserId(userId)
    }

    fun deleteDish(id : String) {
        baseDishesRepository.deleteDishesEntityById(id)
    }

    fun findDishByID(id: String): DishesEntity {
        return baseDishesRepository.findDishesEntityById(id)
    }
}
