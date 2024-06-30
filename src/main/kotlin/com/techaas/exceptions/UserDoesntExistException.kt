package com.techaas.exceptions

class UserDoesntExistException(message: String) : RuntimeException(message) {
    fun getNameOfClass(): String {
        return "User doesn't exist"
    }

}