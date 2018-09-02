package com.progressor.progressor.views.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.progressor.progressor.R
import com.progressor.progressor.components.MainComponentInterface
import com.progressor.progressor.views.presenter.LoginPresenter
import kotlinx.android.synthetic.main.layout_login.*
import javax.inject.Inject

class LoginFragment: BaseFragment(), LoginPresenter.View {

    private var firebaseUser: FirebaseUser? = null
    private var firebaseAuth: FirebaseAuth? = null

    @Inject lateinit var presenter: LoginPresenter

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
        firebaseAuth = FirebaseAuth.getInstance();

        mainSignIn.setOnClickListener {
            presenter.login(mainEmail.text.toString(), mainPassword.text.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        firebaseUser = firebaseAuth?.getCurrentUser()
    }

    override fun getFirebaseAuth(): FirebaseAuth? {
        return firebaseAuth
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