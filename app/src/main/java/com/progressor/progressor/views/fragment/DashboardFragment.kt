package com.progressor.progressor.views.fragment

import android.os.Bundle
import android.view.View
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.views.presenter.DashboardPresenter
import kotlinx.android.synthetic.main.layout_dashboard.*
import javax.inject.Inject

class DashboardFragment : BaseFragment(), DashboardPresenter.View {

    @Inject
    lateinit var presenter: DashboardPresenter

    private fun initialize() {
        dashboardUserEmail.text = authenticationManager.firebaseUser?.email
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDependencies()
        initialize()
    }
    
    override fun injectDependencies() {
        (activity as MainComponentInterface).mainComponent.inject(this)
        presenter.setPresenter(this)
    }

    override fun getFragmentLayout(): Int {
        return R.layout.layout_dashboard
    }
}