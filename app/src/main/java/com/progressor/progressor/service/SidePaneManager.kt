package com.progressor.progressor.service

import android.app.Activity
import android.graphics.Color
import android.view.View
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.view.LoginFragment
import kotlinx.android.synthetic.main.layout_main.*
import kotlinx.android.synthetic.main.layout_main.view.*
import kotlinx.android.synthetic.main.layout_slider.view.*
import javax.inject.Inject

class SidePaneManager constructor(private val mainActivity: Activity) {
    @Inject
    lateinit var authenticationManager: AuthenticationManager
    @Inject
    lateinit var fragmentNavigator: FragmentNavigator

    init {
        (mainActivity as MainComponentInterface).mainComponent?.inject(this)
    }

    fun showSidePane(show: Boolean) {
        mainActivity.mainActivityContainer.slidingPane.slidingPane.setSliderFadeColor(Color.TRANSPARENT)
        if(show) {
            mainActivity.mainActivityContainer.slidingPane.sliderLayout.isEnabled = true
            mainActivity.mainActivityContainer.slidingPane.sliderLayout.isClickable = true
            mainActivity.mainActivityContainer.slidingPane.sliderLayout.visibility = View.VISIBLE
            setSliderAttributes()
        } else {
            mainActivity.mainActivityContainer.slidingPane.closePane()
            mainActivity.mainActivityContainer.slidingPane.sliderLayout.isEnabled = false
            mainActivity.mainActivityContainer.slidingPane.sliderLayout.isClickable = false
            mainActivity.mainActivityContainer.slidingPane.sliderLayout.visibility = View.GONE
        }
    }

    private fun setSliderAttributes() {
        authenticationManager.firebaseUser?.let { firebaseUser ->
            mainActivity.mainActivityContainer.slidingPane.sliderLayout.sliderDisplayName.text = firebaseUser.displayName
        }
        mainActivity.mainActivityContainer.slidingPane.sliderLayout.sliderLogOut.setOnClickListener {
            logOut()
        }
    }

    private fun logOut() {
        authenticationManager.signOut()
        fragmentNavigator.to(LoginFragment())
    }
}