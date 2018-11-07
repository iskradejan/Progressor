package com.progressor.progressor.model.dataobjects.workout.dejan

data class DayThree (
    val deadlift: LinkedHashMap<HashMap<Int, Int>, Int>,
    val wideGripPullDown: LinkedHashMap<HashMap<Int, Int>, Int>,
    val seatedCableRow: LinkedHashMap<HashMap<Int, Int>, Int>,
    val hyperExtension: LinkedHashMap<HashMap<Int, Int>, Int>,
    val tricepExtension: LinkedHashMap<HashMap<Int, Int>, Int>,
    val dips: LinkedHashMap<HashMap<Int, Int>, Int>,
    val dumbbellKickback: LinkedHashMap<HashMap<Int, Int>, Int>,
    val skullcrushers: LinkedHashMap<HashMap<Int, Int>, Int>,
    val legRaise: LinkedHashMap<Int, Int>,
    val plank: LinkedHashMap<Int, Int>,
    val sidePlank: LinkedHashMap<Int, Int>,
    val cableCrunch: LinkedHashMap<HashMap<Int, Int>, Int>
)