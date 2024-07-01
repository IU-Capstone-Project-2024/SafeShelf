package com.techaas.exceptions

class UserAlreadyExists(message: String) : RuntimeException(message) {
    fun getNameOfClass(): String {
        return "User already exists"
    }

}