package com.progressor.progressor.model.dataobjects.account

data class Profile(
        val workouts: MutableList<Workout>? = null,
        val bodyHistory: MutableList<Body>? = null
)