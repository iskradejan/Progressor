package com.progressor.progressor.model.dataobjects.account

import java.time.LocalDateTime

data class Workout (
    val id: Int,
    val name: String? = "",
    val startDate: LocalDateTime ?= null
)