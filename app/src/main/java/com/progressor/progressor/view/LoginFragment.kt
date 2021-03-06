package com.progressor.progressor.view

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.model.constant.FirebaseConstant
import com.progressor.progressor.model.dataobjects.helper.FirebaseResponse
import com.progressor.progressor.service.RxBus
import com.progressor.progressor.presenter.LoginPresenter
import kotlinx.android.synthetic.main.layout_login.*
import javax.inject.Inject

class LoginFragment : BaseFragment(), LoginPresenter.View {

    @Inject
    lateinit var presenter: LoginPresenter

    fun initialize() {
        RxBus.subscribe<FirebaseResponse>(this) {
            if (it.getType().equals(FirebaseConstant.TYPE_LOGIN)) {
                when (it.getSuccess()) {
                    true -> {
                        presenter.route()
                    }
                    false -> {
                        it.getErrors()?.forEach { error ->
                            when (error) {
                                FirebaseConstant.ERROR_USER_NOT_FOUND -> {
                                    loginEmailValueError.text = context?.getString(R.string.login_error_email_not_found)?:"--"
                                    loginEmailValueError.visibility = View.VISIBLE
                                }

                                FirebaseConstant.ERROR_WRONG_PASSWORD -> {
                                    loginPasswordValueError.text = context?.getString(R.string.login_error_password_incorrect)?:"--"
                                    loginPasswordValueError.visibility = View.VISIBLE
                                }
                                else -> {
                                    loginEmailValueError.text = "Generic Error"
                                    loginEmailValueError.visibility = View.VISIBLE
                                }
                            }
                        }
                    }
                }
            }
        }

        loginSignInButton.setOnClickListener {
            presenter.login(loginEmailValue.text.toString(), loginPasswordValue.text.toString())
        }

        loginRegisterButton.setOnClickListener {
            if(!authenticationManager.isLoggedIn()) {
                fragmentNavigator.to(AccountCreateFragment())
            }
        }

        loginForgotPasswordButton.setOnClickListener {
            if(!authenticationManager.isLoggedIn()) {
                fragmentNavigator.to(PasswordResetFragment())
            }
        }
    }

    override fun isFormValid(): Boolean {
        var valid = true

        if (TextUtils.isEmpty(loginEmailValue.getText().toString())) {
            loginEmailValue.setError("Required")
            valid = false
        } else {
            loginEmailValue.setError(null)
        }

        if (TextUtils.isEmpty(loginPasswordValue.getText().toString())) {
            loginPasswordValue.setError("Required")
            valid = false
        } else {
            loginPasswordValue.setError(null)
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
        return R.layout.layout_login
    }
}