package com.progressor.progressor.view

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.model.constant.FirebaseConstant
import com.progressor.progressor.model.dataobjects.helper.FirebaseResponse
import com.progressor.progressor.presenter.NewDejanDayFourPresenter
import com.progressor.progressor.service.RxBus
import kotlinx.android.synthetic.main.layout_new_dejan_day_four.*
import javax.inject.Inject

class NewDejanDayFourFragment : BaseFragment(), NewDejanDayFourPresenter.View {
    @Inject
    lateinit var presenter: NewDejanDayFourPresenter
    lateinit var defaultLabelColor: ColorStateList
    lateinit var currentView: View

    private fun initialize() {
        RxBus.subscribe<FirebaseResponse>(this) {
            if (it.getType().equals(FirebaseConstant.TYPE_DEJAN_DAY_FOUR)) {
                when (it.getSuccess()) {
                    true -> {
                        context.let { context ->
                            Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
                        }
                    }
                    false -> {
                        context.let { context ->
                            Toast.makeText(context, "Trouble saving. Try again", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        defaultLabelColor = newDejanDayFourArcTrainerLabel.textColors

        newDejanWorkoutDayOne.setOnClickListener {
            fragmentNavigator.to(NewDejanDayOneFragment())
        }
        newDejanWorkoutDayTwo.setOnClickListener {
            fragmentNavigator.to(NewDejanDayTwoFragment())
        }
        newDejanWorkoutDayThree.setOnClickListener {
            fragmentNavigator.to(NewDejanDayThreeFragment())
        }
        newDejanWorkoutDayFive.setOnClickListener {
            fragmentNavigator.to(NewDejanDayFiveFragment())
        }
        newDejanDayFourSaveButton.setOnClickListener {
            presenter.save()
        }
    }

    override fun getArcTrainer(): String {
        return newDejanDayFourArcTrainerAmount.text.toString()
    }

    override fun isFormValid(): Boolean {
        var valid = true

        if (newDejanDayFourArcTrainerAmount.text.toString().isEmpty() || newDejanDayFourArcTrainerAmount.text.toString().length > 4) {
            newDejanDayFourArcTrainerAmount.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.baseMaroon, null))
            valid = false
        } else {
            newDejanDayFourArcTrainerAmount.backgroundTintList = defaultLabelColor
            valid = true
        }

        return valid
    }

    override fun onStop() {
        super.onStop()
        RxBus.unsubscribe(this)
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
        return R.layout.layout_new_dejan_day_four
    }
}