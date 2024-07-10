package com.techaas.lifestyle

class SedentaryWorkHighLifestyle: Lifestyle {
    override fun getName(): String {
        return "Sedentary work, intense sports"
    }

    override fun getCoefficient(): Double {
        return 1.4
    }
}