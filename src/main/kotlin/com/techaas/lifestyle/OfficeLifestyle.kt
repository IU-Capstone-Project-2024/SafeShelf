package com.techaas.lifestyle

class OfficeLifestyle: Lifestyle {
    override fun getName(): String {
        return "Office worker"
    }

    override fun getCoefficient(): Double {
        return 1.2
    }
}