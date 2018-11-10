package com.progressor.progressor.presenter

import android.content.Context
import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.service.UserManager
import com.progressor.progressor.view.NewDejanDayThreeFragment
import javax.inject.Inject

class NewDejanDayThreePresenter @Inject constructor(
        var fragmentNavigator: FragmentNavigator,
        var authenticationManager: AuthenticationManager,
        var userManager: UserManager) {

    private lateinit var view: View
    private var context: Context? = null

    interface View {

    }

    fun setPresenter(newDejanDayThreeFragment: NewDejanDayThreeFragment) {
        view = newDejanDayThreeFragment
        context = newDejanDayThreeFragment.context
    }
}