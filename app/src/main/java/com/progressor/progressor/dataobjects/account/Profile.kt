package com.progressor.progressor.dataobjects.account

data class Profile(
        val workouts: MutableList<Workout>? = null,
        val bodyHistory: MutableList<Body>? = null
)