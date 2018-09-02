package com.progressor.progressor.components

import com.progressor.progressor.MainActivity
import com.progressor.progressor.modules.ApiModule
import com.progressor.progressor.modules.FragmentModule
import com.progressor.progressor.services.ApiUtil
import com.progressor.progressor.services.FragmentNavigator
import com.progressor.progressor.views.fragment.BaseFragment
import com.progressor.progressor.views.fragment.DashboardFragment
import com.progressor.progressor.views.fragment.LoginFragment
import com.progressor.progressor.views.fragment.SplashFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApiModule::class),(FragmentModule::class)])
interface UtilComponent {
    // Views
    fun inject(mainActivity: MainActivity)
    fun inject(baseFragment: BaseFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(splashFragment: SplashFragment)
    fun inject(dashboardFragment: DashboardFragment)

    // Services
    fun inject(apiUtil: ApiUtil)
    fun inject(fragmentNavigator: FragmentNavigator)
}