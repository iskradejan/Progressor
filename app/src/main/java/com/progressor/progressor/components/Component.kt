package com.progressor.progressor.components

import com.progressor.progressor.MainActivity
import com.progressor.progressor.views.activity.SplashActivity
import com.progressor.progressor.modules.ApiModule
import com.progressor.progressor.modules.FragmentModule
import com.progressor.progressor.services.FragmentNavigator
import com.progressor.progressor.views.activity.DashboardActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApiModule::class),(FragmentModule::class)])
interface Component {
    // Views
    fun inject(mainActivity: MainActivity)
    fun inject(splashActivity: SplashActivity)
    fun inject(dashboardActivity: DashboardActivity)

    // Services
    fun inject(fragmentNavigator: FragmentNavigator)
}