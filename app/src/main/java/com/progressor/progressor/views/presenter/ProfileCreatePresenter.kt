package com.progressor.progressor.views.presenter

import android.app.Activity
import android.content.Context
import com.progressor.progressor.services.AuthenticationManager
import com.progressor.progressor.services.FragmentNavigator
import com.progressor.progressor.views.fragment.ProfileCreateFragment
import java.time.LocalDateTime
import javax.inject.Inject

class ProfileCreatePresenter @Inject constructor(private var fragmentNavigator: FragmentNavigator, private var authenticationManager: AuthenticationManager) {
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

    }

    fun createProfile(gender: Int?, height: Int?, weight: Int?, dob: LocalDateTime?, createDate: LocalDateTime) {
        if (view.isFormValid()) {
//            authenticationManager.createAccount(context as Activity, email, password, displayName)
        } else {
        }
    }
}