package com.progressor.progressor.service

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.progressor.progressor.R
import com.progressor.progressor.view.*

class FragmentNavigator constructor(private val fragmentManager: FragmentManager) {
    fun to(fragment: Fragment) {
        fragmentManager.findFragmentById(R.id.mainFragmentContainer)?.let {
            if (it::class != fragment::class) {
                startTransaction(fragment)
            }
        } ?: run {
            startTransaction(fragment)
        }
    }

    fun clearBackStack() {
        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    fun manage(authenticationManager: AuthenticationManager, userManager: UserManager) {
        if (authenticationManager.isLoggedIn()) {
            if (!authenticationManager.isVerified()) {
                to(EmailVerifyFragment())
            } else {
                if (userManager.user?.person == null) {
                    to(ProfileCreateFragment())
                } else if (userManager.user?.bodyHistory?.size == 0 && userManager.user?.workouts?.size == 0) {
                    to(EmptyDashboardFragment())
                } else {
                    to(DashboardFragment())
                }
            }
        } else {
            to(LoginFragment())
        }
    }

    private fun startTransaction(fragment: Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFragmentContainer, fragment, fragment::class.java.simpleName).commitNow()
        clearBackStack()
    }
}