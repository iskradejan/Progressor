package com.progressor.progressor.view

import com.progressor.progressor.presenter.DashboardPresenter
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import javax.inject.Inject

class DashboardFragment: BaseFragment(), DashboardPresenter.View {
    @Inject
    lateinit var presenter: DashboardPresenter

    override fun setSidePane() {
        sidePaneManager.showSidePane(true)
    }

    override fun injectDependencies() {
        (activity as MainComponentInterface).mainComponent.inject(this)
        presenter.setPresenter(this)
    }

    override fun getFragmentLayout(): Int {
        return R.layout.layout_dashboard
    }
}