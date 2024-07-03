package com.techaas.exceptions

class LoginPasswordMismatchException(message: String) : RuntimeException(message) {
    fun getName(): String {
        return "Incorrect password"
    }

}