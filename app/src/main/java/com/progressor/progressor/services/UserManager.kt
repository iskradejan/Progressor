package com.progressor.progressor.services

import android.app.Activity
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.model.dataobjects.account.Person
import com.progressor.progressor.model.dataobjects.account.User
import java.time.LocalDateTime
import javax.inject.Inject

class UserManager constructor(private val mainActivity: Activity) {

    @Inject
    lateinit var fragmentNavigator: FragmentNavigator

    var user: User? = null

    init {
        (mainActivity as MainComponentInterface).mainComponent?.inject(this)
    }

    fun createPerson(gender: Int, dob: LocalDateTime, height: Int, weight: Int) {
        val person = Person(gender = gender, dob = dob, height = height, weight = weight, createDate = LocalDateTime.now())
        user = User(person = person)
    }
}