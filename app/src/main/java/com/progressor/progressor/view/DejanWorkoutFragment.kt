package com.progressor.progressor.view

import android.os.Bundle
import android.view.View
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.presenter.DejanWorkoutPresenter
import kotlinx.android.synthetic.main.layout_dejan_workout.*
import javax.inject.Inject

class DejanWorkoutFragment : BaseFragment(), DejanWorkoutPresenter.View {
    @Inject
    lateinit var presenter: DejanWorkoutPresenter

    lateinit var currentView: View

    private fun initialize() {
        dejanWorkoutStartButton.setOnClickListener {
            presenter.manageWorkout()
            fragmentNavigator.to(NewDejanDayOneFragment())
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
        return R.layout.layout_dejan_workout
    }
}