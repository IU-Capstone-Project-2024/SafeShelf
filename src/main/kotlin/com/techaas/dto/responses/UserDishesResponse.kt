package com.techaas.dto.responses

import com.techaas.data_entities.DishType
import com.techaas.domain.entity.IngredientEntity

data class UserDishesResponse (
    val id: String,
    val name: String,
    val ingredients: List<IngredientEntity>,
    val description: String,
    val type: DishType,
)