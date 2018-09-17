package com.progressor.progressor.views.fragment

import android.os.Bundle
import android.view.View
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.views.presenter.EmailVerifyPresenter
import kotlinx.android.synthetic.main.layout_email_verify.*
import javax.inject.Inject

class EmailVerifyFragment : BaseFragment(), EmailVerifyPresenter.View {
    @Inject
    lateinit var presenter : EmailVerifyPresenter

    fun initialize() {
        emailVerifyButton.setOnClickListener {
            presenter.sendEmail()
        }

        emailVerifyLoginButton.setOnClickListener {
            fragmentNavigator.navigate(LoginFragment())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDependencies()
        initialize()
    }

    override fun injectDependencies() {
        (activity as MainComponentInterface).mainComponent.inject(this)
        presenter.setPresenter(this)
        initialize()
    }

    override fun getFragmentLayout(): Int {
        return R.layout.layout_email_verify
    }
}