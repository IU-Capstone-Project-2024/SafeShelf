package com.techaas.exceptions

class IncorrectParameterException(message: String) : RuntimeException(message) {
    fun getName(): String {
        return "Incorrect parameters"
    }
}