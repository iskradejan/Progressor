package com.progressor.progressor.service

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.progressor.progressor.R

class FragmentNavigator constructor(private val fragmentManager: FragmentManager) {
    fun to(fragment: Fragment) {
        fragmentManager.findFragmentById(R.id.mainFragmentContainer)?.let {
            if (it::class != fragment::class) {
                startTransaction(fragment)
            }
        } ?: run {
            startTransaction(fragment)
        }
    }

    fun clearBackStack() {
        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    private fun startTransaction(fragment: Fragment) {
        fragmentManager.executePendingTransactions()
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFragmentContainer, fragment, fragment::class.java.simpleName).commitNow()
        clearBackStack()
    }
}