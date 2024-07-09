package com.techaas.dto

import java.math.BigDecimal
import java.sql.Timestamp
import java.time.LocalDate

data class ProductWithDate(
    val id: Int,
    val name: String,
    val weight: BigDecimal,
    val kcal: BigDecimal,
    val proteins: BigDecimal,
    val fats: BigDecimal,
    val carbohydrates: BigDecimal,
    val date: LocalDate
)
