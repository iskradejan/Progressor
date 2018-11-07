package com.progressor.progressor.presenter

import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.service.UserManager
import com.progressor.progressor.view.NewDejanWorkoutFragment
import javax.inject.Inject

class NewDejanWorkoutPresenter @Inject constructor(
        var fragmentNavigator: FragmentNavigator,
        var userManager: UserManager,
        var authenticationManager: AuthenticationManager) {

    private lateinit var view: View

    interface View {
    }

    fun setPresenter(newDejanWorkoutFragment: NewDejanWorkoutFragment) {
        view = newDejanWorkoutFragment
    }
}