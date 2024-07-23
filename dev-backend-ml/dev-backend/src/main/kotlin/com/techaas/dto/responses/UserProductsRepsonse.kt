package com.techaas.dto.responses

import com.techaas.data_entities.Sex
import java.math.BigDecimal

data class UserProductsRepsonse(
    val id: Long,
    val name: String,
    val weight: BigDecimal,
    val kcal: BigDecimal,
    val proteins: BigDecimal,
    val fats: BigDecimal,
    val carbohydrates: BigDecimal,
    val date: String
)