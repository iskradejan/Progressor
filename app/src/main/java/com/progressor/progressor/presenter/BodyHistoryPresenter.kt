package com.progressor.progressor.presenter

import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.service.UserManager
import com.progressor.progressor.view.BodyHistoryFragment
import com.progressor.progressor.view.LoginFragment
import javax.inject.Inject

class BodyHistoryPresenter @Inject constructor(private var fragmentNavigator: FragmentNavigator, private var userManager: UserManager, private var authenticationManager: AuthenticationManager) {
    private lateinit var view: View

    interface View {

    }

    fun setPresenter(bodyHistoryFragment: BodyHistoryFragment) {
        view = bodyHistoryFragment
        initialize()
    }

    fun initialize() {
        if (!authenticationManager.isLoggedIn()) {
            fragmentNavigator.navigate(LoginFragment())
        }
    }
}