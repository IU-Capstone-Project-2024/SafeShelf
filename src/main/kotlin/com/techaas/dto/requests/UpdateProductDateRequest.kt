package com.techaas.dto.requests

import java.time.LocalDate

data class UpdateProductDateRequest (
    val login: String,
    val productID: Long,
    val date: LocalDate
)