package com.progressor.progressor.views.fragment

import android.os.Bundle
import android.view.View
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.services.BodyCalculator
import com.progressor.progressor.views.presenter.EmptyDashboardPresenter
import kotlinx.android.synthetic.main.layout_empty_dashboard.*
import javax.inject.Inject

class EmptyDashboardFragment : BaseFragment(), EmptyDashboardPresenter.View {

    @Inject
    lateinit var presenter: EmptyDashboardPresenter

    private fun initialize() {
        emptyDashboardWorkoutGfx.setOnClickListener {
            emptyDashboardWorkoutGfx.setImageResource(R.drawable.workout_gfx_active)
            emptyDashboardBodyGfx.setImageResource(R.drawable.body_gfx_inactive)
        }

        emptyDashboardBodyGfx.setOnClickListener {
            emptyDashboardWorkoutGfx.setImageResource(R.drawable.workout_gfx_inactive)
            emptyDashboardBodyGfx.setImageResource(R.drawable.body_gfx_active)
        }

        emptyDashboardContinueButton.setOnClickListener {
            val bodyCalculator = BodyCalculator()
            println("Male Fat: " + bodyCalculator.calculateMaleFat(190.0, 30.5))
            println("Female Fat: " + bodyCalculator.calculateFemaleFat(125.0, 6.0, 24.0, 38.0, 9.5))
        }
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
        sidePaneManager.showSidePane(true)
    }

    override fun getFragmentLayout(): Int {
        return R.layout.layout_empty_dashboard
    }
}