package com.progressor.progressor.service

import java.text.DecimalFormat

class BodyCalculator {
    private val format = DecimalFormat("#.##")

    fun calculateMaleFatPercentage(weight: Double, waist: Double): Double {
        val firstStep = (weight * 1.082) + 94.42
        val leanBodyWeight = firstStep - (waist * 4.15)
        val thirdStep = ((weight - leanBodyWeight) * 100) / weight

        val fatPercentage = format.format(thirdStep).toDouble()

        return fatPercentage
    }

    fun calculateFemaleFatPercentage(weight: Double, wrist: Double, waist: Double, hip: Double, forearm: Double): Double {
        val firstStep = (weight * 0.732) + 8.987
        val secondStep = wrist / 3.14
        val thirdStep = waist * 0.157
        val fourthStep = hip * 0.249
        val fifthStep = forearm * 0.434
        val sixthStep = firstStep + secondStep
        val seventhStep = sixthStep - thirdStep
        val eightStep = seventhStep - fourthStep
        val leanBodyWeight = fifthStep + eightStep
        val tenthStep = ((weight - leanBodyWeight) * 100) / weight

        return format.format(tenthStep).toDouble()
    }

    fun calculateMusclePercentage(fat: Double): Double {
        return 100 - (fat + 15.00)
    }
}