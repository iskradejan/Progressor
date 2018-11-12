package com.progressor.progressor.model.dataobjects.workout.dejan

data class DayFive (
    val squats: LinkedHashMap<HashMap<String, String>, String>,
    val romanianDeadlift: LinkedHashMap<HashMap<String, String>, String>,
    val pistolSquats: LinkedHashMap<HashMap<String, String>, String>,
    val lunges: LinkedHashMap<HashMap<String, String>, String>,
    val cableFrontRaise: LinkedHashMap<HashMap<String, String>, String>,
    val pushPress: LinkedHashMap<HashMap<String, String>, String>,
    val dumbbellLateralRaise: LinkedHashMap<HashMap<String, String>, String>,
    val hangingLegRaise: LinkedHashMap<String, String>,
    val bicycles: LinkedHashMap<String, String>,
    val russianTwists: LinkedHashMap<String, String>
)