package com.techaas.dto.responses

data class LoginAccountResponse(
    val accessToken: String,
    val refreshToken: String
)