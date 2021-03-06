package com.progressor.progressor.di.modules

import android.support.v4.app.FragmentManager
import com.progressor.progressor.MainActivity
import com.progressor.progressor.service.FragmentNavigator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class FragmentModule(private val mainActivity: MainActivity) {

    private val fragmentManager: FragmentManager = mainActivity.supportFragmentManager

    @Provides
    @Singleton
    fun getFragmentNavigator(): FragmentNavigator {
        return FragmentNavigator(fragmentManager)
    }
}