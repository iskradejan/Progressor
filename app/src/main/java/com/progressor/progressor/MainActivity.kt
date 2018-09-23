package com.progressor.progressor

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.progressor.progressor.di.components.DaggerUtilComponent
import com.progressor.progressor.di.modules.ApiModule
import javax.inject.Inject
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.di.components.UtilComponent
import com.progressor.progressor.di.modules.FragmentModule
import com.progressor.progressor.services.FragmentNavigator
import com.progressor.progressor.views.fragment.LoginFragment
import com.progressor.progressor.views.fragment.SplashFragment

class MainActivity : AppCompatActivity(), MainComponentInterface {
    private var utilComponent: UtilComponent? = null

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    @Inject
    lateinit var fragmentNavigator: FragmentNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)
        injectDependencies()
        if (sharedPreferences.getBoolean("firstTime", true)) {
            fragmentNavigator.navigate(SplashFragment())
        } else {
            fragmentNavigator.navigate(LoginFragment())
        }
    }

    override fun getMainComponent(): UtilComponent? {
        utilComponent?.let { return it }

        utilComponent = DaggerUtilComponent
                .builder()
                .apiModule(ApiModule(this))
                .fragmentModule(FragmentModule(this))
                .build()

        return utilComponent
    }

    private fun injectDependencies() {
        mainComponent?.inject(this)
    }
}
