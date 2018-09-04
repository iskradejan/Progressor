package com.progressor.progressor.views.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.progressor.progressor.R
import com.progressor.progressor.components.MainComponentInterface
import com.progressor.progressor.views.presenter.AccountCreatePresenter
import kotlinx.android.synthetic.main.layout_account_create.*
import kotlinx.android.synthetic.main.layout_login.*
import javax.inject.Inject

class AccountCreateFragment: BaseFragment(), AccountCreatePresenter.View {
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

    override fun isFormValid(): Boolean {
        var valid = true

        if (TextUtils.isEmpty(accountCreateEmail.getText().toString())) {
            accountCreateEmail.setError("Required.")
            valid = false
        } else {
            accountCreateEmail.setError(null)
        }

        if (TextUtils.isEmpty(accountCreatePassword.getText().toString())) {
            accountCreatePassword.setError("Required.")
            valid = false
        } else {
            accountCreatePassword.setError(null)
        }

        return valid
    }


    fun initialize() {
        accountCreateRegister.setOnClickListener {
            presenter.register(accountCreateEmail.text.toString(), accountCreatePassword.text.toString())
        }
    }
}