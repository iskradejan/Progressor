package com.progressor.progressor.model.dataobjects.account

import java.time.LocalDateTime

data class User(
        val person: Person ?= null,
        val workouts: MutableList<Workout>? = null,
        val bodyHistory: MutableList<Body>? = null,
        val active: Boolean = true,
        val createDate: LocalDateTime ?= null
)