package com.progressor.progressor.views.presenter

import com.progressor.progressor.services.AuthenticationManager
import com.progressor.progressor.services.FragmentNavigator
import com.progressor.progressor.views.fragment.DashboardFragment
import javax.inject.Inject

class DashboardPresenter @Inject constructor(private var fragmentNavigator: FragmentNavigator, private var authenticationManager: AuthenticationManager) {
    private lateinit var view: View

    interface View {}

    fun setPresenter(dashboardFragment: DashboardFragment) {
        view = dashboardFragment
        initialize()
    }

    fun initialize() {}
}