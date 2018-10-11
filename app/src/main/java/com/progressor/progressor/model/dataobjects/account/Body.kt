package com.progressor.progressor.model.dataobjects.account

data class Body(
        val weight: Double? = null,
        val fatPercentage: Int? = null,
        val musclePercentage: Int? = null,
        val mood: Int = 0,
        val createDate: String = ""
)