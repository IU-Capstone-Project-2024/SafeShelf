package com.techaas.dto.requests

data class AddUserRequest (
    val login: String,
    val name: String,
    val surname: String,
    val password: String,
    val age: Int,
    val sex: String
)