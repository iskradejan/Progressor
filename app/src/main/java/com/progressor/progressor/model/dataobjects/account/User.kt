package com.progressor.progressor.model.dataobjects.account

import com.progressor.progressor.model.dataobjects.workout.dejan.Workout

data class User(
        val person: Person ?= null,
        var workout: Workout? = null,
        var bodyHistory: MutableList<Body>? = ArrayList(),
        val active: Boolean = true,
        val createDate: String = ""
)