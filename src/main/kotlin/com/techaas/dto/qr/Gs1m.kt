package com.techaas.dto.qr

data class Gs1m(
    val gtin: String,
    val sernum: String,
    val productIdType: Int,
    val rawProductCode: String
)