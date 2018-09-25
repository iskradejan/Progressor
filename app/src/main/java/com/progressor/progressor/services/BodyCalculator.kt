package com.progressor.progressor.services

import java.text.DecimalFormat

class BodyCalculator {
    private val format = DecimalFormat("#.##")

    fun calculateMaleFat(weight: Double, waist: Double) : Double {
        val firstStep = ( weight * 1.082 ) + 94.42
        val secondStep = firstStep - ( waist * 4.15 )
        val thirdStep = ( ( weight - secondStep ) * 100 ) / weight

        return format.format(thirdStep).toDouble()
    }

    fun calculateFemaleFat(weight: Double, wrist: Double, waist: Double, hip: Double, forearm: Double) : Double {
        val firstStep = ( weight * 0.732 ) + 8.987
        val secondStep = wrist / 3.14
        val thirdStep = waist * 0.157
        val fourthStep = hip * 0.249
        val fifthStep = forearm * 0.434
        val sixthStep = firstStep + secondStep
        val seventhStep = sixthStep - thirdStep
        val eightStep = seventhStep - fourthStep
        val ninthStep = fifthStep + eightStep
        val tenthStep = ( ( weight - ninthStep) * 100) / weight

        return format.format(tenthStep).toDouble()
    }
}