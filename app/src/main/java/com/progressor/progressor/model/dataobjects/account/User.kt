package com.progressor.progressor.model.dataobjects.account

import java.time.LocalDateTime

data class User(
        val person: Person ?= null,
        val workouts: MutableList<Workout>? = ArrayList(),
        var bodyHistory: MutableList<Body>? = ArrayList(),
        val active: Boolean = true,
        val createDate: String = ""
)