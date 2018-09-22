package com.progressor.progressor.services

import android.app.Activity
import android.view.View
import com.progressor.progressor.di.components.MainComponentInterface
import kotlinx.android.synthetic.main.layout_main.*
import kotlinx.android.synthetic.main.layout_main.view.*

class SidePaneManager constructor(private val mainActivity: Activity) {
    init {
        (mainActivity as MainComponentInterface).mainComponent?.inject(this)
    }

    fun showSidePane(show: Boolean) {
        if(show) {
            println("show")
            mainActivity.mainActivityContainer.slidingPane.sliderLayout.isEnabled = true
            mainActivity.mainActivityContainer.slidingPane.sliderLayout.isClickable = true
            mainActivity.mainActivityContainer.slidingPane.sliderLayout.visibility = View.VISIBLE
        } else {
            println("hide")
            mainActivity.mainActivityContainer.slidingPane.sliderLayout.isEnabled = false
            mainActivity.mainActivityContainer.slidingPane.sliderLayout.isClickable = false
            mainActivity.mainActivityContainer.slidingPane.sliderLayout.visibility = View.GONE
        }
    }
}