package com.progressor.progressor.view

import android.os.Bundle
import android.view.View
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.presenter.NewDejanDayTwoPresenter
import com.progressor.progressor.service.DejanCreator
import com.progressor.progressor.service.ExerciseCreator
import kotlinx.android.synthetic.main.layout_new_dejan_day_two.*
import javax.inject.Inject

class NewDejanDayTwoFragment : BaseFragment(), NewDejanDayTwoPresenter.View {
    @Inject
    lateinit var presenter: NewDejanDayTwoPresenter
    private var exerciseCreator = ExerciseCreator()
    private var dejanCreator = DejanCreator()

    lateinit var currentView: View

    private fun initialize() {
        newDejanWorkoutDayOne.setOnClickListener {
            fragmentNavigator.to(NewDejanWorkoutFragment())
        }

        newDejanWorkoutDayThree.setOnClickListener {
            fragmentNavigator.to(NewDejanDayThreeFragment())
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
        return R.layout.layout_new_dejan_day_two
    }
}