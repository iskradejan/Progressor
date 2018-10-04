package com.progressor.progressor.service

import android.app.Activity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.model.constant.FirebaseConstant
import com.progressor.progressor.model.dataobjects.account.Person
import com.progressor.progressor.model.dataobjects.account.User
import com.progressor.progressor.model.dataobjects.helper.FirebaseResponse
import java.time.LocalDateTime
import javax.inject.Inject

class UserManager constructor(private val mainActivity: Activity) {

    @Inject
    lateinit var fragmentNavigator: FragmentNavigator

    var firebaseFirestore = FirebaseFirestore.getInstance()
    var user: User ?= null

    init {
        (mainActivity as MainComponentInterface).mainComponent?.inject(this)
    }

    fun createPerson(gender: Int, dob: String, height: Int, weight: Int): Person {
        return Person(gender = gender, dob = dob, height = height, weight = weight, createDate = LocalDateTime.now().toString())
    }

    fun fetchUser(uid: String) {
        val response = FirebaseResponse()
        response.setType(FirebaseConstant.TYPE_LOGIN)

        val docRef = firebaseFirestore.collection("users").document(uid)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            user = documentSnapshot.toObject<User>(User::class.java)
            response.setSuccess(true)
            RxBus.publish(response)
        }
    }

    fun addUser(uid: String, newUser: User, type: String) {
        val response = FirebaseResponse()
        response.setType(type)

        firebaseFirestore.collection("users")
                .document(uid)
                .set(newUser)
                .addOnSuccessListener(OnSuccessListener<Void> { documentReference ->
                    user = newUser
                    response.setSuccess(true)
                    RxBus.publish(response)
                })
                .addOnFailureListener(OnFailureListener { e ->
                    response.setSuccess(false)
                    RxBus.publish(response)
                })
    }
}