package com.progressor.progressor.view

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.presenter.NewDejanDayFivePresenter
import kotlinx.android.synthetic.main.layout_new_dejan_day_five.*
import javax.inject.Inject

class NewDejanDayFiveFragment : BaseFragment(), NewDejanDayFivePresenter.View {
    @Inject
    lateinit var presenter: NewDejanDayFivePresenter
    lateinit var defaultLabelColor: ColorStateList
    lateinit var currentView: View

    private fun initialize() {
        newDejanWorkoutDayOne.setOnClickListener {
            fragmentNavigator.to(NewDejanDayOneFragment())
        }
        newDejanWorkoutDayTwo.setOnClickListener {
            fragmentNavigator.to(NewDejanDayTwoFragment())
        }
        newDejanWorkoutDayThree.setOnClickListener {
            fragmentNavigator.to(NewDejanDayThreeFragment())
        }
        newDejanWorkoutDayFour.setOnClickListener {
            fragmentNavigator.to(NewDejanDayFourFragment())
        }
        newDejanDayFiveSaveButton.setOnClickListener {
            presenter.save()
        }
    }

    private fun getSets(): ArrayList<EditText> {
        return arrayListOf<EditText>(newDejanDayFiveSquatsSets, newDejanDayFiveRomanianDeadliftSets, newDejanDayFivePistolSquatsSets, newDejanDayFiveLungesSets, newDejanDayFiveCableFrontRaiseSets, newDejanDayFivePushPressSets, newDejanDayFiveDumbbellLateralRaiseSets, newDejanDayFiveHangingLegRaiseSets, newDejanDayFiveBicyclesSets, newDejanDayFiveRussianTwistsSets)
    }

    private fun getReps(): ArrayList<EditText> {
        return arrayListOf<EditText>(newDejanDayFiveSquatsReps, newDejanDayFiveRomanianDeadliftReps, newDejanDayFivePistolSquatsReps, newDejanDayFiveLungesReps, newDejanDayFiveCableFrontRaiseReps, newDejanDayFivePushPressReps, newDejanDayFiveDumbbellLateralRaiseReps, newDejanDayFiveHangingLegRaiseReps, newDejanDayFiveBicyclesReps, newDejanDayFiveRussianTwistsReps)
    }

    private fun getAmounts(): ArrayList<EditText> {
        return arrayListOf<EditText>(newDejanDayFiveSquatsAmount, newDejanDayFiveRomanianDeadliftAmount, newDejanDayFivePistolSquatsAmount, newDejanDayFiveLungesAmount, newDejanDayFiveCableFrontRaiseAmount, newDejanDayFivePushPressAmount, newDejanDayFiveDumbbellLateralRaiseAmount)
    }

    override fun getSquats(): List<String> {
        return listOf(newDejanDayFiveSquatsSets.text.toString(), newDejanDayFiveSquatsReps.text.toString(), newDejanDayFiveSquatsAmount.text.toString())
    }

    override fun getRomanianDeadlifts(): List<String> {
        return listOf(newDejanDayFiveRomanianDeadliftSets.text.toString(), newDejanDayFiveRomanianDeadliftReps.text.toString(), newDejanDayFiveRomanianDeadliftAmount.text.toString())
    }

    override fun getPistolSquats(): List<String> {
        return listOf(newDejanDayFivePistolSquatsSets.text.toString(), newDejanDayFivePistolSquatsReps.text.toString(), newDejanDayFivePistolSquatsAmount.text.toString())
    }

    override fun getLunges(): List<String> {
        return listOf(newDejanDayFiveLungesSets.text.toString(), newDejanDayFiveLungesReps.text.toString(), newDejanDayFiveLungesAmount.text.toString())
    }

    override fun getCableFrontRaise(): List<String> {
        return listOf(newDejanDayFiveCableFrontRaiseSets.text.toString(), newDejanDayFiveCableFrontRaiseReps.text.toString(), newDejanDayFiveCableFrontRaiseAmount.text.toString())
    }

    override fun getPushPress(): List<String> {
        return listOf(newDejanDayFivePushPressSets.text.toString(), newDejanDayFivePushPressReps.text.toString(), newDejanDayFivePushPressAmount.text.toString())
    }

    override fun getDumbbellLateralRaise(): List<String> {
        return listOf(newDejanDayFiveDumbbellLateralRaiseSets.text.toString(), newDejanDayFiveDumbbellLateralRaiseReps.text.toString(), newDejanDayFiveDumbbellLateralRaiseAmount.text.toString())
    }

    override fun getHangingLegRaise(): List<String> {
        return listOf(newDejanDayFiveHangingLegRaiseSets.text.toString(), newDejanDayFiveHangingLegRaiseReps.text.toString())
    }

    override fun getBicycle(): List<String> {
        return listOf(newDejanDayFiveBicyclesSets.text.toString(), newDejanDayFiveBicyclesReps.text.toString())
    }

    override fun getRussianTwists(): List<String> {
        return listOf(newDejanDayFiveRussianTwistsSets.text.toString(), newDejanDayFiveRussianTwistsReps.text.toString())
    }

    override fun isFormValid(): Boolean {
        var invalid = 0

        for(set in getSets()) {
            context?.let { context ->
                context.resources?.let { resources ->
                    if(set.text.isEmpty() || set.text.length > 2) {
                        set.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.baseMaroon, null))
                        invalid++
                    } else {
                        set.backgroundTintList = defaultLabelColor
                    }
                }
            }
        }

        for(rep in getReps()) {
            context?.let { context ->
                context.resources?.let { resources ->
                    if(rep.text.isEmpty() || rep.text.length > 2) {
                        rep.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.baseMaroon, null))
                        invalid++
                    } else {
                        rep.backgroundTintList = defaultLabelColor
                    }
                }
            }
        }

        for(amount in getAmounts()) {
            context?.let { context ->
                context.resources?.let { resources ->
                    if(amount.text.isEmpty() || amount.text.length > 3) {
                        amount.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.baseMaroon, null))
                        invalid++
                    } else {
                        amount.backgroundTintList = defaultLabelColor
                    }
                }
            }
        }

        return invalid == 0
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
        return R.layout.layout_new_dejan_day_five
    }
}