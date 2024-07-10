package com.techaas.lifestyle

class FeetWorkLowLifestyle: Lifestyle {
    override fun getName(): String {
        return "Work on feet (without lifting heavy weights)"
    }

    override fun getCoefficient(): Double {
        return 1.5
    }
}