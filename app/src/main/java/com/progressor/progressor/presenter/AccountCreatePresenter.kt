package com.progressor.progressor.presenter

import android.app.Activity
import android.content.Context
import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.service.UserManager
import com.progressor.progressor.view.*
import javax.inject.Inject

class AccountCreatePresenter @Inject constructor(
        var fragmentNavigator: FragmentNavigator,
        var authenticationManager: AuthenticationManager,
        var userManager: UserManager) {

    private lateinit var view: View
    private var context: Context? = null

    interface View {
        fun isFormValid(): Boolean
    }

    fun setPresenter(accountCreateFragment: AccountCreateFragment) {
        view = accountCreateFragment
        context = accountCreateFragment.context
        initialize()
    }

    fun initialize() {
        fragmentNavigator.manage(authenticationManager, userManager)
    }

    fun register(email: String, password: String, displayName: String) {
        if (view.isFormValid()) {
            authenticationManager.createAccount(context as Activity, email, password, displayName)
        }
    }
}