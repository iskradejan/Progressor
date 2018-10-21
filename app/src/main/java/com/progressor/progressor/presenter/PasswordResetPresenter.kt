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
        fragmentNavigator.manage(authenticationManager, userManager)
    }

    fun resetPassword(email: String) {
        if (view.isFormValid()) {
            authenticationManager.resetPassword(context as Activity, email)
        }
    }
}