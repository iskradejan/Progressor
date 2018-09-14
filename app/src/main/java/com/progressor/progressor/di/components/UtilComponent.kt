package com.progressor.progressor.di.components

import com.progressor.progressor.MainActivity
import com.progressor.progressor.di.modules.ApiModule
import com.progressor.progressor.di.modules.FragmentModule
import com.progressor.progressor.services.ApiRequestor
import com.progressor.progressor.services.AuthenticationManager
import com.progressor.progressor.services.FragmentNavigator
import com.progressor.progressor.views.fragment.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApiModule::class), (FragmentModule::class)])
interface UtilComponent {
    // Views
    fun inject(mainActivity: MainActivity)

    fun inject(baseFragment: BaseFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(splashFragment: SplashFragment)
    fun inject(dashboardFragment: DashboardFragment)
    fun inject(accountCreateFragment: AccountCreateFragment)
    fun inject(profileCreateFragment: ProfileCreateFragment)

    // Services
    fun inject(apiRequestor: ApiRequestor)
    fun inject(fragmentNavigator: FragmentNavigator)
    fun inject(authenticationManager: AuthenticationManager)
}