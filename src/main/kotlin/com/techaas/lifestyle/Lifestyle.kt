package com.techaas.lifestyle

import org.springframework.stereotype.Component

@Component
interface Lifestyle {
    fun getName(): String
    fun getCoefficient(): Double
}