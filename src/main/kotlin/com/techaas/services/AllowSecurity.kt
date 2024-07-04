package com.techaas.services

import org.springframework.stereotype.Component

@Component
interface AllowSecurity {
    fun permitEndpoint(path: String): Boolean
}