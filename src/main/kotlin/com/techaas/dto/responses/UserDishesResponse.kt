package com.techaas.dto.responses

import com.techaas.data_entities.DishType
import com.techaas.dto.IngredientsEntity

data class UserDishesResponse (
    val id: String,
    val name: String,
    val ingredients: List<IngredientsEntity>,
    val description: String,
    val type: DishType,
)