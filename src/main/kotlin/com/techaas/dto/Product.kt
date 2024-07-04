package com.techaas.dto

import java.math.BigDecimal

data class Product(
    val id: Int,
    val name: String,
    val weight: BigDecimal,
    val kcal: BigDecimal,
    val proteins: BigDecimal,
    val fats: BigDecimal,
    val carbohydrates: BigDecimal
)
