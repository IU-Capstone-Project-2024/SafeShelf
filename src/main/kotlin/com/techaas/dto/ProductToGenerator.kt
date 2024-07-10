package com.techaas.dto

import java.math.BigDecimal
import java.time.LocalDate

data class ProductToGenerator(
    val name: String,
    val weight: BigDecimal,
    val kcal: BigDecimal,
    val proteins: BigDecimal,
    val fats: BigDecimal,
    val carbohydrates: BigDecimal,
    val expirationDate: Long
)
