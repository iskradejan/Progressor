package com.progressor.progressor.presenter

import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.view.DashboardFragment
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