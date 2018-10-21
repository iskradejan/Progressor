package com.progressor.progressor.presenter

import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.view.LoginFragment
import javax.inject.Inject
import android.app.Activity
import android.content.Context
import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.UserManager
import com.progressor.progressor.view.EmptyDashboardFragment
import com.progressor.progressor.view.EmailVerifyFragment
import com.progressor.progressor.view.ProfileCreateFragment

class LoginPresenter @Inject constructor(var fragmentNavigator: FragmentNavigator, var authenticationManager: AuthenticationManager, var userManager: UserManager) {

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
        if (authenticationManager.isLoggedIn()) {
            if (!authenticationManager.isVerified()) {
                fragmentNavigator.to(EmailVerifyFragment())
            } else {
                if (userManager.user?.person == null) {
                    fragmentNavigator.to(ProfileCreateFragment())
                } else {
                    fragmentNavigator.to(EmptyDashboardFragment())
                }
            }
        }
    }

    fun login(email: String, password: String) {
        if (view.isFormValid()) {
            authenticationManager.signIn(context as Activity, email, password)
        }
    }
}