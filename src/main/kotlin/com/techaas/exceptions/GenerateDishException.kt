package com.techaas.exceptions

class GenerateDishException(message: String): RuntimeException(message) {
    fun getName(): String {
        return "Error in generating dishes"
    }
}