package com.progressor.progressor.views.presenter

import com.progressor.progressor.services.AuthenticationManager
import com.progressor.progressor.services.FragmentNavigator
import com.progressor.progressor.views.fragment.EmptyDashboardFragment
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