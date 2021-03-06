package com.progressor.progressor.view

import android.os.Bundle
import android.view.View
import com.progressor.progressor.MainActivity
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.presenter.EmailVerifyPresenter
import kotlinx.android.synthetic.main.layout_email_verify.*
import javax.inject.Inject

class EmailVerifyFragment : BaseFragment(), EmailVerifyPresenter.View {
    @Inject
    lateinit var presenter : EmailVerifyPresenter

    fun initialize() {
        (activity as MainActivity).setBackFragment(LoginFragment())

        emailVerifyButton.setOnClickListener {
            presenter.sendEmail()
        }

        emailVerifyLoginButton.setOnClickListener {
            presenter.cancel()
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
        return R.layout.layout_email_verify
    }
}