package com.progressor.progressor.model.dataobjects.workout.dejan

data class Dejan (
    val dayOne: MutableList<DayOne>? = ArrayList(),
    val dayTwo: MutableList<DayTwo>? = ArrayList(),
    val dayThree: MutableList<DayThree>? = ArrayList(),
    val dayFour: MutableList<DayFour>? = ArrayList(),
    val dayFive: MutableList<DayFive>? = ArrayList()
)