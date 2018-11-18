package com.progressor.progressor.view

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.progressor.progressor.MainActivity
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.model.constant.FirebaseConstant
import com.progressor.progressor.model.dataobjects.helper.FirebaseResponse
import com.progressor.progressor.service.RxBus
import com.progressor.progressor.presenter.PasswordResetPresenter
import kotlinx.android.synthetic.main.layout_password_reset.*
import javax.inject.Inject

class PasswordResetFragment : BaseFragment(), PasswordResetPresenter.View {

    @Inject
    lateinit var presenter: PasswordResetPresenter

    private fun initialize() {
        (activity as MainActivity).setBackFragment(LoginFragment())

        RxBus.subscribe<FirebaseResponse>(this) {
            if (it.getType().equals(FirebaseConstant.TYPE_PASSWORD_RESET)) {
                when (it.getSuccess()) {
                    true -> {
                        fragmentNavigator.to(LoginFragment())
                    }
                    false -> {
                        it.getErrors()?.forEach { error ->
                            when (error) {
                                FirebaseConstant.ERROR_USER_NOT_FOUND -> {
                                    passwordResetEmailValueError.text = context?.getString(R.string.password_reset_error_email_not_found) ?: "--"
                                    passwordResetEmailValueError.visibility = View.VISIBLE
                                }
                                FirebaseConstant.ERROR_INVALID_EMAIL -> {
                                    passwordResetEmailValueError.text = context?.getString(R.string.password_reset_error_email_invalid) ?: "--"
                                    passwordResetEmailValueError.visibility = View.VISIBLE
                                }
                            }
                        }
                    }
                }
            }
        }

        passwordResetButton.setOnClickListener {
            passwordResetEmailValueError.visibility = View.GONE
            presenter.resetPassword(passwordResetEmailValue.getText().toString())
        }
    }

    override fun isFormValid(): Boolean {
        var valid = true

        if (TextUtils.isEmpty(passwordResetEmailValue.getText().toString())) {
            passwordResetEmailValue.setError("Required")
            valid = false
        } else {
            passwordResetEmailValue.setError(null)
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
        return R.layout.layout_password_reset
    }
}