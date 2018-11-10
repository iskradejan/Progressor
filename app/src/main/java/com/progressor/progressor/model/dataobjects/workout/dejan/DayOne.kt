package com.progressor.progressor.model.dataobjects.workout.dejan

data class DayOne (
    val inclineChestPress: Array<String>,
    val flatChestPress: Array<String>,
    val chestFlies: Array<String>,
    val bicepCurls: Array<String>,
    val hammerCurls: Array<String>,
    val barbellRollout: Array<String>,
    val flutterKick: Array<String>,
    val starPlank: Array<String>,
    val createDate: String = ""
)