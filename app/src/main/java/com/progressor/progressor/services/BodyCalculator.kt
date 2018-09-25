package com.progressor.progressor.services

import java.text.DecimalFormat

class BodyCalculator {
    private val format = DecimalFormat("#.##")

    fun calculateMaleFat(weight: Double, waist: Double): Double {

        val fat = ((weight - (((weight * 1.082) + 94.42) - (waist * 4.15))) * 100) / weight

        return format.format(fat).toDouble()
    }

    fun calculateFemaleFat(weight: Double, wrist: Double, waist: Double, hip: Double, forearm: Double): Double {

        val fat = ((weight - ((forearm * 0.434) + (((((weight * 0.732) + 8.987) + (wrist / 3.14)) - (waist * 0.157)) - (hip * 0.249)))) * 100) / weight

        return format.format(fat).toDouble()
    }
}