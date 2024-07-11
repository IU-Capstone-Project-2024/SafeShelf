package com.techaas.lifestyle

import org.springframework.stereotype.Component

@Component
class FeetWorkLowLifestyle: Lifestyle {
    override fun getName(): String {
        return "Work on feet (without lifting heavy weights)"
    }

    override fun getCoefficient(): Double {
        return 1.5
    }
}