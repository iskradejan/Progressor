package com.progressor.progressor.components

import com.progressor.progressor.MainActivity
import com.progressor.progressor.views.activity.SplashActivity
import com.progressor.progressor.modules.ApiModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApiModule::class)])
interface ApiComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(splashActivity: SplashActivity)
}