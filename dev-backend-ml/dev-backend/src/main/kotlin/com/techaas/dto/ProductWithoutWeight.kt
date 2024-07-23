package com.techaas.dto

data class ProductWithoutWeight(
    val id: Int,
    val name: String,
    val kcal: String,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double
)