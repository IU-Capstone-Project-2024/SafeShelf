package com.techaas.dto.requests

import com.techaas.data_entities.Sex

data class UpdateUserRequest (
    val oldLogin : String,
    val login: String,
    val name: String,
    val surname: String,
    val password: String,
    val age: Int,
    val sex: Sex,
    val lifestyle: String
)