package com.techaas.exceptions

class UserDoesntExistException(message: String) : RuntimeException(message) {
    fun getName(): String {
        return "User doesn't exist"
    }

}