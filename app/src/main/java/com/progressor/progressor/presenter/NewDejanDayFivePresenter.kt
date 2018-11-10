package com.progressor.progressor.presenter

import android.content.Context
import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.service.UserManager
import com.progressor.progressor.view.NewDejanDayFiveFragment
import javax.inject.Inject

class NewDejanDayFivePresenter @Inject constructor(
        var fragmentNavigator: FragmentNavigator,
        var authenticationManager: AuthenticationManager,
        var userManager: UserManager) {

    private lateinit var view: View
    private var context: Context? = null

    interface View {

    }

    fun setPresenter(newDejanDayFiveFragment: NewDejanDayFiveFragment) {
        view = newDejanDayFiveFragment
        context = newDejanDayFiveFragment.context
    }
}