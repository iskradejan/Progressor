package com.progressor.progressor.presenter

import com.progressor.progressor.view.SplashFragment
import javax.inject.Inject

class SplashPresenter @Inject constructor() {
    private lateinit var view: View

    interface View {}

    fun setPresenter(splashFragment: SplashFragment) {
        view = splashFragment
    }
}