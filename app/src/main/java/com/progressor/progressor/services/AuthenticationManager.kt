package com.progressor.progressor.services

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.progressor.progressor.MainActivity
import com.progressor.progressor.views.fragment.DashboardFragment
import javax.inject.Inject

class AuthenticationManager @Inject constructor() {
    var firebaseAuth: FirebaseAuth? = null
    var firebaseUser: FirebaseUser? = null

    @Inject
    lateinit var fragmentNavigator: FragmentNavigator

    fun signIn(context: Context, email: String, password: String) {
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener(context as Activity) { task ->
            if (task.isSuccessful) {
                firebaseUser = firebaseAuth?.currentUser
                MainActivity.user.login?.email = firebaseUser?.email
                fragmentNavigator.navigate(DashboardFragment())
            } else {
                println("Authentication failed:\n" + task.exception)
                Toast.makeText(context, "Authentication failed...", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun signOut() {
        if(firebaseAuth != null) {
            firebaseAuth?.signOut()
            firebaseUser = null
            fragmentNavigator.clearBackStack()
        }
    }

    fun isLoggedIn(): Boolean {
        if (firebaseUser == null) {
            return false
        }

        return true
    }
}