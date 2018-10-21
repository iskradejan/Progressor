package com.progressor.progressor.view

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.model.constant.FirebaseConstant
import com.progressor.progressor.model.dataobjects.helper.FirebaseResponse
import com.progressor.progressor.service.RxBus
import com.progressor.progressor.presenter.AccountCreatePresenter
import kotlinx.android.synthetic.main.layout_account_create.*
import javax.inject.Inject

class AccountCreateFragment : BaseFragment(), AccountCreatePresenter.View {
    @Inject
    lateinit var presenter: AccountCreatePresenter

    fun initialize() {
        RxBus.subscribe<FirebaseResponse>(this) {
            if (it.getType().equals(FirebaseConstant.TYPE_CREATE_ACCOUNT)) {
                when (it.getSuccess()) {
                    true -> {
                        fragmentNavigator.to(EmailVerifyFragment())
                    }
                    false -> {
                        it.getErrors()?.forEach { error ->
                            when (error) {
                                FirebaseConstant.ERROR_EMAIL_ALREADY_IN_USE -> {
                                    accountCreateEmailValueError.text = getString(R.string.account_create_error_email_exists)
                                    accountCreateEmailValueError.visibility = View.VISIBLE
                                }
                                FirebaseConstant.ERROR_WEAK_PASSWORD -> {
                                    accountCreatePasswordValueError.text = getString(R.string.account_create_error_password_weak)
                                    accountCreatePasswordValueError.visibility = View.VISIBLE
                                }
                            }
                        }
                    }
                }
            }
        }
        accountCreateRegisterButton.setOnClickListener {
            accountCreateEmailValueError.visibility = View.GONE
            accountCreatePasswordValueError.visibility = View.GONE
            accountCreateDisplayNameValueError.visibility = View.GONE
            presenter.register(accountCreateEmailValue.text.toString(), accountCreatePasswordValue.text.toString(), accountCreateDisplayNameValue.text.toString())
        }
        accountCreateLoginButton.setOnClickListener {
            fragmentNavigator.to(LoginFragment())
        }
    }

    override fun isFormValid(): Boolean {
        var valid = true

        if (TextUtils.isEmpty(accountCreateEmailValue.getText().toString())) {
            accountCreateEmailValue.setError("Required")
            valid = false
        } else {
            accountCreateEmailValue.setError(null)
        }

        if (TextUtils.isEmpty(accountCreatePasswordValue.getText().toString())) {
            accountCreatePasswordValue.setError("Required")
            valid = false
        } else {
            accountCreatePasswordValue.setError(null)
        }

        if (TextUtils.isEmpty(accountCreateDisplayNameValue.getText().toString())) {
            accountCreateDisplayNameValue.setError("Required")
            valid = false
        } else if (accountCreateDisplayNameValue.getText().toString().count() < 2) {
            accountCreateDisplayNameValueError.text = getString(R.string.account_create_error_display_name_short)
            accountCreateDisplayNameValueError.visibility = View.VISIBLE
        } else {
            accountCreateDisplayNameValue.setError(null)
        }

        return valid
    }

    override fun onStop() {
        super.onStop()
        RxBus.unsubscribe(this)
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
        return R.layout.layout_account_create
    }
}