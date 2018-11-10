package com.progressor.progressor.model.dataobjects.workout.dejan

data class DayOne (
    val inclineChestPress: List<String>,
    val flatChestPress: List<String>,
    val chestFlies: List<String>,
    val bicepCurls: List<String>,
    val hammerCurls: List<String>,
    val barbellRollout: List<String>,
    val flutterKick: List<String>,
    val starPlank: List<String>,
    val createDate: String = ""
)