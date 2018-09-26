package com.progressor.progressor.model.dataobjects.account

data class Body(
        val weight: Double? = null,
        val fatPercentage: Double? = null,
        val musclePercentage: Double? = null,
        val mood: Int = 0,
        val createDate: String = ""
)