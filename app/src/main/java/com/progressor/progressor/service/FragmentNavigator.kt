package com.progressor.progressor.service

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.progressor.progressor.R
import com.progressor.progressor.view.EmptyDashboardFragment
import com.progressor.progressor.view.LoginFragment

class FragmentNavigator constructor(private val fragmentManager: FragmentManager) {
    fun navigate(fragment: Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()

        if (fragment is LoginFragment || fragment is EmptyDashboardFragment) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            fragmentTransaction
                    .replace(R.id.mainFragmentContainer, fragment, fragment::class.java.simpleName)
                    .commit()
        } else {
            fragmentTransaction
                    .replace(R.id.mainFragmentContainer, fragment, fragment::class.java.simpleName)
                    .addToBackStack(null)
                    .commit()
            fragmentManager.executePendingTransactions()
        }
    }

    fun clearBackStack() {
        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }
}