package com.techaas.dto.responses

data class ErrorResponse(
    val code: Int,
    val message: String,
    val timestamp: String,
    val path: String
)