package com.techaas.dto

data class Dish(
    val recipeTitle: String,
    val ingredients: List<Product>,
    val description: String
)