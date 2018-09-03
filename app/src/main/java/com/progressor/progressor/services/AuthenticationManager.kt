package com.progressor.progressor.services

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.*
import com.progressor.progressor.MainActivity
import com.progressor.progressor.components.MainComponentInterface
import com.progressor.progressor.dataobjects.firebase.FirebaseConstant
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

    fun createAccount(email: String, password: String) {
        if(isLoggedIn()) {
            println("navigating to dashboard cause you are logged in , cant create acount")
            fragmentNavigator.navigate(DashboardFragment())
        }

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener { task ->
            if(task.isSuccessful) {
                println("account created successfully")
                firebaseUser = firebaseAuth?.currentUser
            } else {
                println("account create failed")
                val exception = task.exception as FirebaseAuthException

                when(exception.errorCode) {
                    FirebaseConstant.ERROR_EMAIL_ALREADY_IN_USE -> {
                        println("caught error: email in use")
                    }
                    FirebaseConstant.ERROR_WEAK_PASSWORD -> {
                        println("caught error: weak password")
                    }
                }
            }
        }
    }

    fun signIn(context: Context, email: String, password: String) {
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener(context as Activity) { task ->
            if (task.isSuccessful) {
                firebaseUser = firebaseAuth?.currentUser
                authenticated = true
                MainActivity.user.login?.email = firebaseUser?.email
                fragmentNavigator.navigate(DashboardFragment())
            } else {
                val exception = task.exception as FirebaseAuthException

                when(exception.errorCode) {
                    FirebaseConstant.ERROR_USER_NOT_FOUND -> {
                        println("caught error: user not found")
                    }
                    FirebaseConstant.ERROR_WRONG_PASSWORD -> {
                        println("caught error: wrong password")
                    }
                }
                Toast.makeText(context, "Authentication failed...", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun signOut() {
        if(firebaseUser != null) {
            firebaseAuth?.signOut()
            firebaseUser = null
            fragmentNavigator.clearBackStack()
        }
    }

    fun isLoggedIn(): Boolean {
        return authenticated
    }
}