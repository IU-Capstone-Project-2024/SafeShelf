package com.techaas.dto

import java.math.BigDecimal

data class IngredientsEntity (
    val userProductId : Long,
    val name : String,
    val weight: BigDecimal
    )