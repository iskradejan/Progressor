package com.progressor.progressor.model.dataobjects.workout.dejan

data class DayOne (
    val inclineChestPress: LinkedHashMap<HashMap<Int, Int>, Int>,
    val flatChestPress: LinkedHashMap<HashMap<Int, Int>, Int>,
    val chestFlies: LinkedHashMap<HashMap<Int, Int>, Int>,
    val bicepCurls: LinkedHashMap<HashMap<Int, Int>, Int>,
    val hammerCurls: LinkedHashMap<HashMap<Int, Int>, Int>,
    val barbellRollout: LinkedHashMap<Int, Int>,
    val flutterKick: LinkedHashMap<Int, Int>,
    val starPlank: LinkedHashMap<Int, Int>
)