package com.progressor.progressor.views.presenter

import com.progressor.progressor.services.FragmentNavigator
import com.progressor.progressor.views.fragment.LoginFragment
import javax.inject.Inject
import android.app.Activity
import android.content.Context
import com.progressor.progressor.services.AuthenticationManager
import com.progressor.progressor.views.fragment.DashboardFragment

class LoginPresenter @Inject constructor() {
    private lateinit var view: View
    private var context: Context? = null

    @Inject
    lateinit var fragmentNavigator: FragmentNavigator
    @Inject
    lateinit var authenticationManager: AuthenticationManager

    interface View {
        fun isFormValid(): Boolean
    }

    fun setPresenter(loginFragment: LoginFragment) {
        view = loginFragment
        context = loginFragment.context
        initialize()
    }

    private fun initialize() {
        if (authenticationManager.isLoggedIn()) {
            println("Already logged in, go to Dashboard")
            fragmentNavigator.navigate(DashboardFragment())
        }
    }

    fun login(email: String, password: String) {
        if (view.isFormValid()) {
            authenticationManager.signIn(context as Activity, email, password)
        }
    }
}