package com.progressor.progressor.model.dataobjects.workout.dejan

data class DayThree (
    val deadlift: List<String> ?= null,
    val wideGripPullDown: List<String> ?= null,
    val seatedCableRow: List<String> ?= null,
    val hyperExtension: List<String> ?= null,
    val tricepExtension: List<String> ?= null,
    val dips: List<String> ?= null,
    val dumbbellKickback: List<String> ?= null,
    val skullcrushers: List<String> ?= null,
    val legRaise: List<String> ?= null,
    val plank: List<String> ?= null,
    val cableCrunch: List<String> ?= null,
    val createDate: String = ""
)