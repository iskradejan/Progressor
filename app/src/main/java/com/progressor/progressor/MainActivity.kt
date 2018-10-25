package com.progressor.progressor

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.progressor.progressor.di.components.DaggerUtilComponent
import com.progressor.progressor.di.modules.ApiModule
import javax.inject.Inject
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.di.components.UtilComponent
import com.progressor.progressor.di.modules.FragmentModule
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.view.LoginFragment
import com.progressor.progressor.view.SplashFragment

class MainActivity : AppCompatActivity(), MainComponentInterface {
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    @Inject
    lateinit var fragmentNavigator: FragmentNavigator

    private var utilComponent: UtilComponent? = null
    private var backFragment: Fragment? = null

    fun setBackFragment(fragment:Fragment) {
        backFragment = fragment
    }

    private fun injectDependencies() {
        mainComponent?.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)
        injectDependencies()
        if (sharedPreferences.getBoolean("firstTime", true)) {
            fragmentNavigator.to(SplashFragment())
        } else {
            fragmentNavigator.to(LoginFragment())
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

    override fun onBackPressed() {
        backFragment?.let {
            fragmentNavigator.to(it)
            return
        }

        super.onBackPressed()
    }
}
