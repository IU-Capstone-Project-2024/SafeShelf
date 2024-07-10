package com.techaas.lifestyle

class PhysicalWorkLifestyle: Lifestyle {
    override fun getName(): String {
        return "Daily trainings\\Work associated with physical activity"
    }

    override fun getCoefficient(): Double {
        return 1.7
    }
}