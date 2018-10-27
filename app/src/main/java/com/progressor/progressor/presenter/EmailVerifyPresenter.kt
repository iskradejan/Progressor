package com.progressor.progressor.presenter

import android.content.Context
import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.service.UserManager
import com.progressor.progressor.view.*
import javax.inject.Inject

class EmailVerifyPresenter @Inject constructor(
        var fragmentNavigator: FragmentNavigator,
        var authenticationManager: AuthenticationManager,
        var userManager: UserManager) {

    private lateinit var view: View
    private var context: Context? = null

    interface View {}

    fun setPresenter(emailVerifyFragment: EmailVerifyFragment) {
        view = emailVerifyFragment
        context = emailVerifyFragment.context
    }

    fun sendEmail() {
        authenticationManager.verifyEmail()
        authenticationManager.signOut()
        fragmentNavigator.to(LoginFragment())
    }

    fun cancel() {
        authenticationManager.signOut()
        fragmentNavigator.to(LoginFragment())
    }
}