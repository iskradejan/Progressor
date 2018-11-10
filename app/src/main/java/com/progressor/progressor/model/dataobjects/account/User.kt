package com.progressor.progressor.model.dataobjects.account

data class User(
        val person: Person ?= null,
        var workouts: MutableList<Any>? = ArrayList(),
        var bodyHistory: MutableList<Body>? = ArrayList(),
        val active: Boolean = true,
        val createDate: String = ""
)