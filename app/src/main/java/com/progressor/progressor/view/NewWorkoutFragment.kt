package com.progressor.progressor.view

import android.os.Bundle
import android.view.View
import com.progressor.progressor.MainActivity
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.presenter.NewWorkoutPresenter
import kotlinx.android.synthetic.main.layout_new_workout.*
import javax.inject.Inject

class NewWorkoutFragment : BaseFragment(), NewWorkoutPresenter.View {
    @Inject
    lateinit var presenter: NewWorkoutPresenter

    lateinit var currentView: View

    private fun initialize() {
        (activity as MainActivity).setBackFragment(DashboardFragment())

        newWorkoutDejan.setOnClickListener {
            fragmentNavigator.to(DejanWorkoutFragment())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        injectDependencies()
        setSidePane()
        initialize()
    }

    override fun setSidePane() {
        sidePaneManager.showSidePane(true)
    }

    override fun injectDependencies() {
        (activity as MainComponentInterface).mainComponent.inject(this)
        presenter.setPresenter(this)
    }

    override fun getFragmentLayout(): Int {
        return R.layout.layout_new_workout
    }
}