package com.techaas.dto.responses

import java.sql.Timestamp

data class ProductResponse(
    val id: Long,
    val name: String,
    val weight: Int,
    val expirationTime: Timestamp
)