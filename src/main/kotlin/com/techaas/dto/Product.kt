package com.techaas.dto

import java.sql.Timestamp

data class Product(
    val id: Long,
    val name: String,
    val weight: Double,
    val expirationTime: Timestamp
)