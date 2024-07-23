package com.techaas.services

import org.springframework.stereotype.Component

@Component("EnableSecurity")
class EnableSecurity: AllowSecurity {
    override fun permitEndpoint(path: String): Boolean {
        return path.startsWith("/account/register") || path.startsWith("/account/login")
    }

}