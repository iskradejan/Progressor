package com.progressor.progressor.presenter

import android.content.Context
import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.view.EmailVerifyFragment
import com.progressor.progressor.view.LoginFragment
import javax.inject.Inject

class EmailVerifyPresenter @Inject constructor(private var fragmentNavigator: FragmentNavigator, private var authenticationManager: AuthenticationManager) {
    private lateinit var view: View
    private var context: Context? = null

    interface View {}

    fun setPresenter(emailVerifyFragment: EmailVerifyFragment) {
        view = emailVerifyFragment
        context = emailVerifyFragment.context
        initialize()
    }

    fun initialize() {
        if (!authenticationManager.isLoggedIn()) {
            fragmentNavigator.navigate(LoginFragment())
        }
    }

    fun sendEmail() {
        authenticationManager.verifyEmail()
        authenticationManager.signOut()
        fragmentNavigator.navigate(LoginFragment())
    }

    fun cancel() {
        authenticationManager.signOut()
        fragmentNavigator.navigate(LoginFragment())
    }
}