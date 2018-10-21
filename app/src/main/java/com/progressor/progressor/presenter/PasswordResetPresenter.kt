package com.progressor.progressor.presenter

import android.app.Activity
import android.content.Context
import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.service.UserManager
import com.progressor.progressor.view.*
import javax.inject.Inject

class PasswordResetPresenter @Inject constructor(
        var fragmentNavigator: FragmentNavigator,
        var authenticationManager: AuthenticationManager,
        var userManager: UserManager) {

    private lateinit var view: View
    private var context: Context? = null

    interface View {
        fun isFormValid(): Boolean
    }

    fun setPresenter(passwordResetFragment: PasswordResetFragment) {
        view = passwordResetFragment
        context = passwordResetFragment.context
        initialize()
    }

    private fun initialize() {
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

    fun resetPassword(email: String) {
        if (view.isFormValid()) {
            authenticationManager.resetPassword(context as Activity, email)
        }
    }
}