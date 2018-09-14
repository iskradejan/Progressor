package com.progressor.progressor.model.dataobjects.account

import java.time.LocalDateTime

data class Person(
        val firstName: String,
        val lastName: String,
        val gender: Int = 0,
        val dob: LocalDateTime,
        val height: Int,
        val createDate: LocalDateTime
)