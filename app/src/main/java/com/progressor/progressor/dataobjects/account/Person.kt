package com.progressor.progressor.dataobjects.account

import java.time.LocalDateTime

data class Person(
        val firstName: String? = "",
        val lastName: String? = "",
        val gender: Int? = null,
        val dob: LocalDateTime? = null,
        val height: Int? = null,
        val ethnicity: Int? = null,
        val createDate: LocalDateTime? = null
)