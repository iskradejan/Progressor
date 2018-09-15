package com.progressor.progressor.views.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.model.constant.FirebaseConstant
import com.progressor.progressor.model.pojo.FirebaseResponse
import com.progressor.progressor.services.RxBus
import com.progressor.progressor.views.presenter.AccountCreatePresenter
import kotlinx.android.synthetic.main.layout_account_create.*
import javax.inject.Inject

class AccountCreateFragment : BaseFragment(), AccountCreatePresenter.View {
    @Inject
    lateinit var presenter: AccountCreatePresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDependencies()
        initialize()
    }

    override fun getFragmentLayout(): Int {
        return R.layout.layout_account_create
    }

    override fun injectDependencies() {
        (activity as MainComponentInterface).mainComponent.inject(this)
        presenter.setPresenter(this)
        initialize()
    }

    override fun onStop() {
        super.onStop()
        RxBus.unsubscribe(this)
    }

    fun initialize() {
        RxBus.subscribe<FirebaseResponse>(this) {
            println("TYPE: " + it.getType())
            if (it.getType().equals(FirebaseConstant.TYPE_CREATE_ACCOUNT)) {
                when (it.getSuccess()) {
                    true -> {
                        fragmentNavigator.navigate(ProfileCreateFragment())
                    }
                    false -> {
                        it.getErrors()?.forEach { error ->
                            when (error) {
                                FirebaseConstant.ERROR_EMAIL_ALREADY_IN_USE -> {
                                    accountCreateEmailError.text = getString(R.string.account_create_error_email_exists)
                                    accountCreateEmailError.visibility = View.VISIBLE
                                }
                                FirebaseConstant.ERROR_WEAK_PASSWORD -> {
                                    accountCreatePasswordError.text = getString(R.string.account_create_error_password_weak)
                                    accountCreatePasswordError.visibility = View.VISIBLE
                                }
                            }
                        }
                    }
                }
            }
        }
        accountCreateRegister.setOnClickListener {
            accountCreateEmailError.visibility = View.GONE
            accountCreatePasswordError.visibility = View.GONE
            accountCreateDisplayNameError.visibility = View.GONE
            presenter.register(accountCreateEmail.text.toString(), accountCreatePassword.text.toString(), accountCreateDisplayName.text.toString())
        }
        accountCreateLogin.setOnClickListener {
            fragmentNavigator.navigate(LoginFragment())
        }
    }

    override fun isFormValid(): Boolean {
        var valid = true

        if(TextUtils.isEmpty(accountCreateEmail.getText().toString())) {
            accountCreateEmail.setError("Required.")
            valid = false
        } else {
            accountCreateEmail.setError(null)
        }

        if(TextUtils.isEmpty(accountCreatePassword.getText().toString())) {
            accountCreatePassword.setError("Required.")
            valid = false
        } else {
            accountCreatePassword.setError(null)
        }

        if(TextUtils.isEmpty(accountCreateDisplayName.getText().toString())) {
            accountCreateDisplayName.setError("Required")
            valid = false
        } else if(accountCreateDisplayName.getText().toString().count() < 2) {
            accountCreateDisplayNameError.text = getString(R.string.account_create_error_display_name_short)
            accountCreateDisplayNameError.visibility = View.VISIBLE
        } else {
            accountCreateDisplayName.setError(null)
        }

        return valid
    }
}