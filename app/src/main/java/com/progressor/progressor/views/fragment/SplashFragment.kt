package com.progressor.progressor.views.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import com.progressor.progressor.MainActivity
import com.progressor.progressor.R
import com.progressor.progressor.components.MainComponentInterface
import com.progressor.progressor.views.presenter.SplashPresenter
import kotlinx.android.synthetic.main.layout_splash.*
import javax.inject.Inject

class SplashFragment: BaseFragment(), SplashPresenter.View {

    @Inject lateinit var presenter: SplashPresenter
    @Inject lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDependencies()
        initialize()
    }

    override fun getFragmentLayout(): Int {
        return R.layout.layout_splash
    }

    override fun injectDependencies() {
        (activity as MainComponentInterface).mainComponent.inject(this)
        presenter.setPresenter(this)
    }

    private fun initialize() {
        splashHeader.text = MainActivity.user.person?.firstName ?: "none"
        splashSubHeader.text = MainActivity.user.person?.lastName ?: "none"

        sharedPreferences.edit().putBoolean("firstTime", false).apply()

        splashLogin.setOnClickListener {
            fragmentNavigator.navigate(LoginFragment())
        }
    }
}