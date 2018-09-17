package com.progressor.progressor.model.dataobjects.account

import java.time.LocalDateTime

data class Person(
        val gender: Int,
        val dob: LocalDateTime,
        val height: Int,
        val weight: Int,
        val createDate: LocalDateTime
)