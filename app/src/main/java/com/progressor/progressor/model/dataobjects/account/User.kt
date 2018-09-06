package com.progressor.progressor.model.dataobjects.account

data class User(
        val login: Login? = null,
        val person: Person? = null,
        val profile: Profile? = null,
        val active: Boolean = true
)