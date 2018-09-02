package com.progressor.progressor.views.presenter

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.progressor.progressor.MainActivity
import com.progressor.progressor.services.FragmentNavigator
import com.progressor.progressor.views.fragment.LoginFragment
import javax.inject.Inject
import android.app.Activity
import android.content.Context
import com.progressor.progressor.views.fragment.DashboardFragment

class LoginPresenter @Inject constructor(){
    private lateinit var view: View
    private var context: Context? = null

    @Inject
    lateinit var fragmentNavigator: FragmentNavigator

    interface View {
        fun getFirebaseAuth(): FirebaseAuth?
        fun isFormValid(): Boolean
    }

    fun setPresenter(loginFragment: LoginFragment) {
        view = loginFragment
        context = loginFragment.context
    }

    fun login(email: String, password: String) {
        if (view.isFormValid()) {
            view.getFirebaseAuth()?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    MainActivity.user.login?.email = view.getFirebaseAuth()?.getCurrentUser()?.email
                    fragmentNavigator.navigate(DashboardFragment())
                } else {
                    println("Authentication failed:\n" + task.exception)
                    Toast.makeText(context, "Authentication failed...", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}