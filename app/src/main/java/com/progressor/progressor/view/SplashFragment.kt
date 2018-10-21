package com.progressor.progressor.view

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.presenter.SplashPresenter
import kotlinx.android.synthetic.main.layout_splash.*
import javax.inject.Inject

class SplashFragment : BaseFragment(), SplashPresenter.View {

    @Inject
    lateinit var presenter: SplashPresenter
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private fun initialize() {
//        splashHeader.text = MainActivity.user.person?.firstName ?: "none"
//        splashSubHeader.text = MainActivity.user.person?.lastName ?: "none"

        sharedPreferences.edit().putBoolean("firstTime", false).apply()

        splashLogin.setOnClickListener {
            fragmentNavigator.to(LoginFragment())
        }

        splashRegister.setOnClickListener {
            fragmentNavigator.to(AccountCreateFragment())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDependencies()
        setSidePane()
        initialize()
    }

    override fun injectDependencies() {
        (activity as MainComponentInterface).mainComponent.inject(this)
        presenter.setPresenter(this)
    }

    override fun setSidePane() {
        sidePaneManager.showSidePane(false)
    }

    override fun getFragmentLayout(): Int {
        return R.layout.layout_splash
    }
}