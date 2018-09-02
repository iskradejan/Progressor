package com.progressor.progressor.services

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.progressor.progressor.R

class FragmentNavigator constructor(private val fragmentManager: FragmentManager){
    fun navigate(fragment: Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        println("FRAGMENT ID: " + fragment.id)
        println("FRAGMENT NAME: " + fragment::class.java.simpleName)
        fragmentTransaction
                .replace(R.id.fragmentContainer, fragment, fragment::class.java.simpleName)
                .addToBackStack(null)
                .commit()
        fragmentManager.executePendingTransactions()
    }
}