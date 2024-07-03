package com.techaas.data_entities

data class Product(
    val id: Int,
    val name: String,
    val weight: String,
    val kcal: String,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double
)
