package com.techaas.lifestyle

import org.springframework.stereotype.Component

@Component
class OfficeLifestyle: Lifestyle {
    override fun getName(): String {
        return "Office worker"
    }

    override fun getCoefficient(): Double {
        return 1.2
    }
}