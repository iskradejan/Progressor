package com.progressor.progressor.Services

import com.progressor.progressor.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApiModule::class)])
interface ApiComponent {
    fun inject(mainActivity: MainActivity)
}