package com.techaas.dto.qr

data class Receipt(
    val items: List<Item>,
    val user: String,
    val dateTime: String,
    val totalSum: Int
)