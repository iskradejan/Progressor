package com.progressor.progressor.views.presenter

import com.progressor.progressor.services.FragmentNavigator
import com.progressor.progressor.views.fragment.DashboardFragment
import javax.inject.Inject

class DashboardPresenter @Inject constructor() {
    private lateinit var view: View

    @Inject
    lateinit var fragmentNavigator: FragmentNavigator

    interface View {}

    fun setPresenter(dashboardFragment: DashboardFragment) {
        view = dashboardFragment
    }
}