package com.progressor.progressor.services

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.progressor.progressor.R

class FragmentNavigator constructor(private val fragmentManager: FragmentManager){
    fun navigate(fragment: Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
//                R.anim.fragment_slide_left_exit,
//                R.anim.fragment_slide_right_enter,
//                R.anim.fragment_slide_right_exit)
// replace default_activity_button with legit layout element
        fragmentTransaction
                .replace(R.id.default_activity_button, fragment, fragment::class.java.simpleName)
                .addToBackStack(null)
                .commit()
        fragmentManager.executePendingTransactions()
    }
}