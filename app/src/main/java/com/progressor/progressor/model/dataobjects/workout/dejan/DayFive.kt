package com.progressor.progressor.model.dataobjects.workout.dejan

data class DayFive (
    val squats: LinkedHashMap<HashMap<Int, Int>, Int>,
    val romanianDeadlift: LinkedHashMap<HashMap<Int, Int>, Int>,
    val pistolSquats: LinkedHashMap<HashMap<Int, Int>, Int>,
    val lunges: LinkedHashMap<HashMap<Int, Int>, Int>,
    val cableFrontRaise: LinkedHashMap<HashMap<Int, Int>, Int>,
    val pushPress: LinkedHashMap<HashMap<Int, Int>, Int>,
    val dumbbellLateralRaise: LinkedHashMap<HashMap<Int, Int>, Int>,
    val hangingLegRaise: LinkedHashMap<Int, Int>,
    val bicycles: LinkedHashMap<Int, Int>,
    val russianTwists: LinkedHashMap<Int, Int>
)