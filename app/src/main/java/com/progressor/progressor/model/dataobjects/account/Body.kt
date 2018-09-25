package com.progressor.progressor.model.dataobjects.account

import java.time.LocalDateTime

data class Body(
        val weight: Double? = null,
        val fatPercentage: Double? = null,
        val musclePercentage: Double? = null,
        val waterPerfentage: Double? = null,
        val mood: String? = null,
        val createDate: LocalDateTime? = null
)