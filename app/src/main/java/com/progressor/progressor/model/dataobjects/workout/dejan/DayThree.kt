package com.progressor.progressor.model.dataobjects.workout.dejan

data class DayThree (
    val deadlift: LinkedHashMap<HashMap<String, String>, String>,
    val wideGripPullDown: LinkedHashMap<HashMap<String, String>, String>,
    val seatedCableRow: LinkedHashMap<HashMap<String, String>, String>,
    val hyperExtension: LinkedHashMap<HashMap<String, String>, String>,
    val tricepExtension: LinkedHashMap<HashMap<String, String>, String>,
    val dips: LinkedHashMap<HashMap<String, String>, String>,
    val dumbbellKickback: LinkedHashMap<HashMap<String, String>, String>,
    val skullcrushers: LinkedHashMap<HashMap<String, String>, String>,
    val legRaise: LinkedHashMap<String, String>,
    val plank: LinkedHashMap<String, String>,
    val cableCrunch: LinkedHashMap<HashMap<String, String>, String>
)