package com.techaas.data_entities

data class ProductWithoutWeight(
    val id: Int,
    val name: String,
    val kcal: String,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double
)