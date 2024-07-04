package com.techaas.dto

import java.math.BigDecimal
import java.sql.Timestamp

data class ProductWithDate(
    val id: Int,
    val name: String,
    val weight: BigDecimal,
    val kcal: BigDecimal,
    val proteins: BigDecimal,
    val fats: BigDecimal,
    val carbohydrates: BigDecimal,
    val date: Timestamp
)
