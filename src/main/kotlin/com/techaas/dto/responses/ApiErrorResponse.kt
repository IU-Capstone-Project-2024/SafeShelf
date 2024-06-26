package com.techaas.dto.responses

data class ApiErrorResponse (
    val code: String,
    val description: String,
    val exceptionName: String,
    val exceptionMessage: String,
    val stackRace: List<String>
)