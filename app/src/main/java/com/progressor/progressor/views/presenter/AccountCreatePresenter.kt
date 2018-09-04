package com.progressor.progressor.views.presenter

import android.app.Activity
import android.content.Context
import com.progressor.progressor.services.AuthenticationManager
import com.progressor.progressor.services.FragmentNavigator
import com.progressor.progressor.views.fragment.AccountCreateFragment
import com.progressor.progressor.views.fragment.DashboardFragment
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
            fragmentNavigator.navigate(DashboardFragment())
        }
    }

    fun register(email: String, password: String) {
        if (view.isFormValid()) {
            authenticationManager.createAccount(context as Activity, email, password)
        }
    }
}