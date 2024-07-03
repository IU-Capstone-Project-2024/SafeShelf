package com.techaas.tools

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomEncoder {
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()
}