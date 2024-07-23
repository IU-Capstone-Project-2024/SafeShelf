package com.techaas.domain.entity

import java.math.BigDecimal

data class IngredientEntity(
    val userProductId: Long,
    val name: String,
    val weight: BigDecimal
)