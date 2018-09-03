package com.progressor.progressor.views.presenter

import com.progressor.progressor.views.fragment.SplashFragment
import javax.inject.Inject

class SplashPresenter @Inject constructor() {
    private lateinit var view: View

    interface View {}

    fun setPresenter(splashFragment: SplashFragment) {
        view = splashFragment
    }
}