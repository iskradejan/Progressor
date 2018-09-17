package com.progressor.progressor.views.presenter

import android.content.Context
import com.progressor.progressor.services.AuthenticationManager
import com.progressor.progressor.services.FragmentNavigator
import com.progressor.progressor.services.UserManager
import com.progressor.progressor.views.fragment.DashboardFragment
import com.progressor.progressor.views.fragment.EmailVerifyFragment
import com.progressor.progressor.views.fragment.LoginFragment
import com.progressor.progressor.views.fragment.ProfileCreateFragment
import java.time.LocalDateTime
import javax.inject.Inject

class ProfileCreatePresenter @Inject constructor(
        private var fragmentNavigator: FragmentNavigator,
        private var authenticationManager: AuthenticationManager,
        private var userManager: UserManager) {

    private lateinit var view: View
    private var context: Context? = null

    interface View {
        fun isFormValid(): Boolean
    }

    fun setPresenter(profileCreateFragment: ProfileCreateFragment) {
        view = profileCreateFragment
        context = profileCreateFragment.context
        initialize()
    }

    fun initialize() {
        if (!authenticationManager.isLoggedIn()) {
            fragmentNavigator.navigate(LoginFragment())
        }
        if (!authenticationManager.isVerified()) {
            fragmentNavigator.navigate(EmailVerifyFragment())
        }
    }

    fun createProfile(gender: Int?, height: Int?, weight: Int?, dob: LocalDateTime?) {
        if (view.isFormValid()) {
            userManager.createPerson(gender!!, dob!!, height!!, weight!!)
            fragmentNavigator.navigate(DashboardFragment())
        }
    }
}