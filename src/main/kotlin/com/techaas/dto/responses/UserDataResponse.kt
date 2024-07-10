package com.techaas.dto.responses

import com.techaas.data_entities.Sex

data class UserDataResponse(
    val login: String,
    val password: String,
    val name: String,
    val surname: String,
    val age: Int,
    val sex: Sex,
    val lifestyle: String
)