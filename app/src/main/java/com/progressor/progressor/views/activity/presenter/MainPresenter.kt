package com.progressor.progressor.views.activity.presenter

import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.progressor.progressor.MainActivity
import com.progressor.progressor.views.activity.DashboardActivity
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(){

    private lateinit var view: View
    private lateinit var activity: MainActivity

    interface View {
        // function defined here means it must be implemented inside the activity || if you need to use it, you have to reference it from the view... view.function
        fun getFirebaseAuth(): FirebaseAuth?
        fun isFormValid(): Boolean
    }

    fun setPresenter(mainActivity: MainActivity) {
        view = mainActivity
        activity = mainActivity
        init()
    }

    fun init() {
    }

    fun login(email: String, password: String) {
        if (view.isFormValid()) {
            view.getFirebaseAuth()?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    MainActivity.user.login?.email = view.getFirebaseAuth()?.getCurrentUser()?.email
                    val dashboardIntent = Intent(activity.baseContext, DashboardActivity::class.java)
                    activity.startActivity(dashboardIntent)
                } else {
                    println("Authentication failed:\n" + task.exception)
                    Toast.makeText(activity.baseContext, "Authentication failed...", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}