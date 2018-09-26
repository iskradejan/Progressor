package com.progressor.progressor.views.presenter

import com.progressor.progressor.services.AuthenticationManager
import com.progressor.progressor.services.FragmentNavigator
import com.progressor.progressor.views.fragment.NewBodyFragment
import javax.inject.Inject

class NewBodyPresenter @Inject constructor(private var fragmentNavigator: FragmentNavigator, private var authenticationManager: AuthenticationManager) {
    private lateinit var view: View

    interface View {
        fun isFormValid(): Boolean
    }

    fun setPresenter(newBodyFragment: NewBodyFragment) {
        view = newBodyFragment
        initialize()
    }

    fun initialize() {

    }

    fun addBody() {
        if (view.isFormValid()) {

        }
    }
}