package com.progressor.progressor.presenter

import com.progressor.progressor.service.FragmentNavigator
import javax.inject.Inject
import android.app.Activity
import android.content.Context
import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.UserManager
import com.progressor.progressor.view.*

class LoginPresenter @Inject constructor(
        var fragmentNavigator: FragmentNavigator,
        var authenticationManager: AuthenticationManager,
        var userManager: UserManager) {

    private lateinit var view: View
    private var context: Context? = null

    interface View {
        fun isFormValid(): Boolean
    }

    fun setPresenter(loginFragment: LoginFragment) {
        view = loginFragment
        context = loginFragment.context
        initialize()
    }

    private fun initialize() {
        route()
    }

    fun route() {
        if (authenticationManager.isLoggedIn()) {
            if (!authenticationManager.isVerified()) {
                fragmentNavigator.to(EmailVerifyFragment())
            } else {
                if (userManager.user?.person == null) {
                    fragmentNavigator.to(ProfileCreateFragment())
                } else if (userManager.user?.bodyHistory?.size == 0 && userManager.user?.workouts?.size == 0) {
                    fragmentNavigator.to(EmptyDashboardFragment())
                } else {
                    fragmentNavigator.to(DashboardFragment())
                }
            }
        } else {
            fragmentNavigator.to(LoginFragment())
        }
    }

    fun login(email: String, password: String) {
        if (view.isFormValid()) {
            authenticationManager.signIn(context as Activity, email, password)
        }
    }
}