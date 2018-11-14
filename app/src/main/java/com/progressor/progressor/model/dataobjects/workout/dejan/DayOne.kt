package com.progressor.progressor.model.dataobjects.workout.dejan

data class DayOne (
    val inclineChestPress: List<String> ?= null,
    val flatChestPress: List<String> ?= null,
    val chestFlies: List<String> ?= null,
    val bicepCurls: List<String> ?= null,
    val hammerCurls: List<String> ?= null,
    val barbellRollout: List<String> ?= null,
    val flutterKick: List<String> ?= null,
    val starPlank: List<String> ?= null,
    val createDate: String = ""
)