package com.progressor.progressor.dataobjects.account

data class User(
        val login: Login? = null,
        val person: Person? = null,
        val profile: Profile? = null,
        val active: Boolean = true
)