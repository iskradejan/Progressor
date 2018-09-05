package com.progressor.progressor.services

import android.app.Activity
import android.content.Context
import com.google.firebase.auth.*
import com.progressor.progressor.components.MainComponentInterface
import com.progressor.progressor.dataobjects.firebase.FirebaseConstant
import com.progressor.progressor.dataobjects.helper.FirebaseResponse
import com.progressor.progressor.views.fragment.DashboardFragment
import javax.inject.Inject

class AuthenticationManager constructor(private val mainActivity: Activity) {

    var firebaseAuth: FirebaseAuth? = null
    var firebaseUser: FirebaseUser? = null
    var authenticated : Boolean = false

    @Inject lateinit var fragmentNavigator: FragmentNavigator

    init {
        (mainActivity as MainComponentInterface).mainComponent?.inject(this)
    }

    fun createAccount(context:Context, email: String, password: String) {
        if(isLoggedIn()) {
            println("navigating to dashboard cause you are logged in , cant create acount")
            fragmentNavigator.navigate(DashboardFragment())
        }

        val response = FirebaseResponse()
        response.setType(FirebaseConstant.TYPE_CREATE_ACCOUNT)

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener(context as Activity) { task ->
            if(task.isSuccessful) {
                println("account created successfully")
                firebaseUser = firebaseAuth?.currentUser
                authenticated = true

                response.setSuccess(true)
                RxBus.publish(response)
            } else {
                val exception = task.exception as FirebaseAuthException

                response.setSuccess(false)
                val errors : MutableList<String> = ArrayList()

                when(exception.errorCode) {
                    FirebaseConstant.ERROR_EMAIL_ALREADY_IN_USE -> {
                        errors.add(FirebaseConstant.ERROR_EMAIL_ALREADY_IN_USE)
                    }
                    FirebaseConstant.ERROR_WEAK_PASSWORD -> {
                        errors.add(FirebaseConstant.ERROR_WEAK_PASSWORD)
                    }
                }
                response.setErrors(errors)
                RxBus.publish(response)
            }
        }
    }

    fun signIn(context: Context, email: String, password: String) {
        val response = FirebaseResponse()
        response.setType(FirebaseConstant.TYPE_LOGIN)

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener(context as Activity) { task ->
            if (task.isSuccessful) {
                firebaseUser = firebaseAuth?.currentUser
                authenticated = true

                response.setSuccess(true)
                RxBus.publish(response)
            } else {
                val exception = task.exception as FirebaseAuthException
                response.setSuccess(false)
                val errors : MutableList<String> = ArrayList()

                when(exception.errorCode) {
                    FirebaseConstant.ERROR_USER_NOT_FOUND -> {
                        errors.add(FirebaseConstant.ERROR_USER_NOT_FOUND)
                    }
                    FirebaseConstant.ERROR_WRONG_PASSWORD -> {
                        errors.add(FirebaseConstant.ERROR_WRONG_PASSWORD)
                    }
                }
                response.setErrors(errors)
                RxBus.publish(response)
            }
        }
    }

    fun signOut() {
        if(firebaseUser != null) {
            firebaseAuth?.signOut()
            firebaseUser = null
            authenticated = false
            fragmentNavigator.clearBackStack()
        }
    }

    fun isLoggedIn(): Boolean {
        return authenticated
    }
}