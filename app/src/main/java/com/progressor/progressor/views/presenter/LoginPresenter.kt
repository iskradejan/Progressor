package com.progressor.progressor.views.presenter

import com.progressor.progressor.services.FragmentNavigator
import com.progressor.progressor.views.fragment.LoginFragment
import javax.inject.Inject
import android.app.Activity
import android.content.Context
import com.progressor.progressor.services.AuthenticationManager
import com.progressor.progressor.services.UserManager
import com.progressor.progressor.views.fragment.DashboardFragment
import com.progressor.progressor.views.fragment.EmailVerifyFragment
import com.progressor.progressor.views.fragment.ProfileCreateFragment

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
        if (authenticationManager.isLoggedIn()) {
            if (!authenticationManager.isVerified()) {
                fragmentNavigator.navigate(EmailVerifyFragment())
            } else {
                if (userManager.user?.person == null) {
                    fragmentNavigator.navigate(ProfileCreateFragment())
                } else {
                    fragmentNavigator.navigate(DashboardFragment())
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