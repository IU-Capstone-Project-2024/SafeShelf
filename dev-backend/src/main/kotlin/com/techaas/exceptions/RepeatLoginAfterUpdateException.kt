package com.techaas.exceptions

class RepeatLoginAfterUpdateException(message: String): RuntimeException(message) {
    fun getName(): String {
        return "Repeat login after updating"
    }
}