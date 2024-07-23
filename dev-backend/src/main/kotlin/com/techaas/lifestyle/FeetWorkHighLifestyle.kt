package com.techaas.lifestyle

import org.springframework.stereotype.Component

@Component
class FeetWorkHighLifestyle: Lifestyle {
    override fun getName(): String {
        return "Work on feet (without lifting heavy weights), 3 times a week intense sport"
    }

    override fun getCoefficient(): Double {
        return 1.6
    }
}