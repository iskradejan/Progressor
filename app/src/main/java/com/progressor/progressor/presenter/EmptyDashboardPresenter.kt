package com.progressor.progressor.presenter

import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.view.EmptyDashboardFragment
import javax.inject.Inject

class EmptyDashboardPresenter @Inject constructor(private var fragmentNavigator: FragmentNavigator, private var authenticationManager: AuthenticationManager) {
    private lateinit var view: View

    interface View {}

    fun setPresenter(emptyDashboardFragment: EmptyDashboardFragment) {
        view = emptyDashboardFragment
        initialize()
    }

    fun initialize() {}
}