package com.progressor.progressor.presenter

import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.view.EmptyDashboardFragment
import javax.inject.Inject

class EmptyDashboardPresenter @Inject constructor(
        var fragmentNavigator: FragmentNavigator,
        var authenticationManager: AuthenticationManager) {
    
    private lateinit var view: View

    interface View {}

    fun setPresenter(emptyDashboardFragment: EmptyDashboardFragment) {
        view = emptyDashboardFragment
    }
}