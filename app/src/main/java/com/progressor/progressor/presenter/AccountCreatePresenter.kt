package com.progressor.progressor.presenter

import android.app.Activity
import android.content.Context
import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.view.AccountCreateFragment
import com.progressor.progressor.view.EmptyDashboardFragment
import javax.inject.Inject

class AccountCreatePresenter @Inject constructor(private var fragmentNavigator: FragmentNavigator, private var authenticationManager: AuthenticationManager) {
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
        if (authenticationManager.isLoggedIn()) {
            fragmentNavigator.to(EmptyDashboardFragment())
        }
    }

    fun register(email: String, password: String, displayName: String) {
        if (view.isFormValid()) {
            authenticationManager.createAccount(context as Activity, email, password, displayName)
        }
    }
}