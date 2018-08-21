package com.progressor.progressor.dataobjects.account

data class User(
        val person: Person? = null,
        val profile: Profile? = null,
        val active: Boolean = true
)