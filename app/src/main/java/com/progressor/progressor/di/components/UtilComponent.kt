package com.progressor.progressor.di.components

import com.progressor.progressor.MainActivity
import com.progressor.progressor.di.modules.ApiModule
import com.progressor.progressor.di.modules.FragmentModule
import com.progressor.progressor.service.*
import com.progressor.progressor.view.*
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
    fun inject(emptyDashboardFragment: EmptyDashboardFragment)
    fun inject(accountCreateFragment: AccountCreateFragment)
    fun inject(profileCreateFragment: ProfileCreateFragment)
    fun inject(passwordResetFragment: PasswordResetFragment)
    fun inject(emailVerifyFragment: EmailVerifyFragment)
    fun inject(newBodyFragment: NewBodyFragment)
    fun inject(bodyHistoryFragment: BodyHistoryFragment)
    fun inject(dashboardFragment: DashboardFragment)
    fun inject(newWorkoutFragment: NewWorkoutFragment)
    fun inject(dejanWorkoutFragment: DejanWorkoutFragment)
    fun inject(newDejanDayOneFragment: NewDejanDayOneFragment)
    fun inject(newDejanDayTwoFragment: NewDejanDayTwoFragment)
    fun inject(newDejanDayThreeFragment: NewDejanDayThreeFragment)
    fun inject(newDejanDayFourFragment: NewDejanDayFourFragment)
    fun inject(newDejanDayFiveFragment: NewDejanDayFiveFragment)

    // Services
    fun inject(apiRequestor: ApiRequestor)
    fun inject(fragmentNavigator: FragmentNavigator)
    fun inject(authenticationManager: AuthenticationManager)
    fun inject(userManager: UserManager)
    fun inject(sidePaneManager: SidePaneManager)
}