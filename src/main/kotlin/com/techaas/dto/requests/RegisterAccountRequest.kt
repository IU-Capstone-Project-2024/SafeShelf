package com.techaas.dto.requests

import com.techaas.data_entities.Sex

data class RegisterAccountRequest(
    val login: String,
    val password: String,
    val name: String,
    val surname: String,
    val age: Int,
    val sex: Sex
)