package com.progressor.progressor.views.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.progressor.progressor.R
import com.progressor.progressor.components.MainComponentInterface
import com.progressor.progressor.dataobjects.firebase.FirebaseConstant
import com.progressor.progressor.dataobjects.helper.FirebaseResponse
import com.progressor.progressor.services.RxBus
import com.progressor.progressor.views.presenter.LoginPresenter
import kotlinx.android.synthetic.main.layout_login.*
import javax.inject.Inject

class LoginFragment : BaseFragment(), LoginPresenter.View {

    @Inject
    lateinit var presenter: LoginPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDependencies()
        initialize()
    }

    override fun getFragmentLayout(): Int {
        return R.layout.layout_login
    }

    override fun injectDependencies() {
        (activity as MainComponentInterface).mainComponent.inject(this)
        presenter.setPresenter(this)
    }

    fun initialize() {
        RxBus.listen(FirebaseResponse::class.java).subscribe {
            if (it.getType().equals(FirebaseConstant.TYPE_LOGIN)) {
                when (it.getSuccess()) {
                    true -> {
                        fragmentNavigator.navigate(DashboardFragment())
                    }
                    false -> {
                        it.getErrors()?.forEach {
                            when (it) {
                                FirebaseConstant.ERROR_USER_NOT_FOUND -> {
                                    println("inside login email setter for error")
                                    mainEmailError.text = context?.getString(R.string.login_error_email_not_found)?:"--"
                                    mainEmailError.visibility = View.VISIBLE
                                }

                                FirebaseConstant.ERROR_WRONG_PASSWORD -> {
                                    mainPasswordError.text = context?.getString(R.string.login_error_password_incorrect)?:"--"
                                    mainPasswordError.visibility = View.VISIBLE
                                }
                            }
                        }
                    }
                }
            }
        }

        mainSignIn.setOnClickListener {
            mainEmailError.visibility = View.GONE
            mainPasswordError.visibility = View.GONE
            presenter.login(mainEmail.text.toString(), mainPassword.text.toString())
        }
        mainRegister.setOnClickListener {
            fragmentNavigator.navigate(AccountCreateFragment())
        }
    }

    override fun isFormValid(): Boolean {
        var valid = true

        if (TextUtils.isEmpty(mainEmail.getText().toString())) {
            mainEmail.setError("Required.")
            valid = false
        } else {
            mainEmail.setError(null)
        }

        if (TextUtils.isEmpty(mainPassword.getText().toString())) {
            mainPassword.setError("Required.")
            valid = false
        } else {
            mainPassword.setError(null)
        }

        return valid
    }
}