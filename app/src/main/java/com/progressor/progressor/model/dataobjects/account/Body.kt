package com.progressor.progressor.model.dataobjects.account

import java.time.LocalDateTime

data class Body(
        val weight: Int? = null,
        val fatPercentage: Int? = null,
        val musclePercentage: Int? = null,
        val waterPerfentage: Int? = null,
        val mood: String? = null,
        val createDate: LocalDateTime? = null
)