package com.techaas.dto.requests

import com.techaas.data_entities.Sex

data class UpdateUserRequest (
    val oldLogin : String,
    val login: String,
    val name: String,
    val surname: String,
    val height: Int,
    val weight: Int,
    val password: String,
    val age: Int,
    val sex: Sex,
    val lifestyle: String,
    val goal: String
)