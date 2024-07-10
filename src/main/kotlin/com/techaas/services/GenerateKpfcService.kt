package com.techaas.services

import com.techaas.data_entities.Sex
import org.springframework.stereotype.Component

@Component
class GenerateKpfcService {
    fun getKpfc (
        weight: Double,
        height: Double,
        age: Int,
        sex: Sex,
        activity: Double,
        goal: String
    ): Array<DoubleArray> {
        var bmr = (10 * weight) + (6.25 * height) - (5 * age)
        when (sex) {
            Sex.M -> bmr += 5
            Sex.F -> bmr -= 161
        }
        bmr *= activity

        val protein: Double
        val fats: Double
        val carbo: Double

        when (goal) {
            "loss" -> {
                bmr *= 0.85
                protein = bmr * 0.35 / 4
                fats = bmr * 0.25 / 9
                carbo = bmr * 0.4 / 4
            }

            "get" -> {
                bmr *= 1.2
                protein = bmr * 0.25 / 4
                fats = bmr * 0.15 / 9
                carbo = bmr * 0.6 / 4
            }

            else -> {
                protein = bmr * 0.3 / 4
                fats = bmr * 0.2 / 9
                carbo = bmr * 0.5 / 4
            }
        }

        val kpfcBreakfast = doubleArrayOf(bmr * 0.3, protein * 0.35, fats * 0.2, carbo * 0.4)
        val kpfcLunch = doubleArrayOf(bmr * 0.4, protein * 0.3, fats * 0.5, carbo * 0.3)
        val kpfcDinner = doubleArrayOf(bmr * 0.3, protein * 0.35, fats * 0.3, carbo * 0.3)

        return arrayOf(kpfcBreakfast, kpfcLunch, kpfcDinner)
    }

}