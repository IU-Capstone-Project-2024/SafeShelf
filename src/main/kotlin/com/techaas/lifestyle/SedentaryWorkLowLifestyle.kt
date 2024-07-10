package com.techaas.lifestyle

class SedentaryWorkLowLifestyle: Lifestyle {
    override fun getName(): String {
        return "Sedentary work, light fitness"
    }

    override fun getCoefficient(): Double {
        return 1.375
    }
}