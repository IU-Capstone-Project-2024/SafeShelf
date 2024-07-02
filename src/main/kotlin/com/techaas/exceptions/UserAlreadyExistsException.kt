package com.techaas.exceptions

class UserAlreadyExistsException(message: String) : RuntimeException(message) {
    fun getName(): String {
        return "User already exists"
    }

}