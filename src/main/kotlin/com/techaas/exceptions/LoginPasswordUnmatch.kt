package com.techaas.exceptions

class LoginPasswordUnmatch(message: String) : RuntimeException(message) {
    fun getNameOfClass(): String {
        return "Incorrect password"
    }

}