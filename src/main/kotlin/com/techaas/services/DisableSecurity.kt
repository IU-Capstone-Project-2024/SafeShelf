package com.techaas.services

import org.springframework.stereotype.Component

@Component("DisableSecurity")
class DisableSecurity: AllowSecurity {
    override fun permitEndpoint(path: String): Boolean {
        return true
    }
}