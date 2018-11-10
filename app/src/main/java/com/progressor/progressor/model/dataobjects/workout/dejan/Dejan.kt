package com.progressor.progressor.model.dataobjects.workout.dejan

data class Dejan (
    var dayOneList: MutableList<DayOne>? = ArrayList(),
    var dayTwoList: MutableList<DayTwo>? = ArrayList(),
    var dayThreeList: MutableList<DayThree>? = ArrayList(),
    var dayFourList: MutableList<DayFour>? = ArrayList(),
    var dayFiveList: MutableList<DayFive>? = ArrayList()
)