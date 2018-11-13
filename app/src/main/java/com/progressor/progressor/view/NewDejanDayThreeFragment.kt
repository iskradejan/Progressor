package com.progressor.progressor.view

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.presenter.NewDejanDayThreePresenter
import kotlinx.android.synthetic.main.layout_new_dejan_day_three.*
import javax.inject.Inject

class NewDejanDayThreeFragment : BaseFragment(), NewDejanDayThreePresenter.View {
    @Inject
    lateinit var presenter: NewDejanDayThreePresenter
    lateinit var defaultLabelColor: ColorStateList
    lateinit var currentView: View

    private fun initialize() {
        newDejanWorkoutDayOne.setOnClickListener {
            fragmentNavigator.to(NewDejanDayOneFragment())
        }
        newDejanWorkoutDayTwo.setOnClickListener {
            fragmentNavigator.to(NewDejanDayTwoFragment())
        }
        newDejanWorkoutDayFour.setOnClickListener {
            fragmentNavigator.to(NewDejanDayFourFragment())
        }
        newDejanWorkoutDayFive.setOnClickListener {
            fragmentNavigator.to(NewDejanDayFiveFragment())
        }
        newDejanDayThreeSaveButton.setOnClickListener {
            presenter.save()
        }
    }

    private fun getSets(): ArrayList<EditText> {
        return arrayListOf<EditText>(newDejanDayThreeDeadliftSets, newDejanDayThreeWideGripPullDownSets, newDejanDayThreeSeatedCableRowSets, newDejanDayThreeHyperExtensionSets, newDejanDayThreeTricepExtensionSets, newDejanDayThreeDipsSets, newDejanDayThreeDumbbellKickbackSets, newDejanDayThreeSkullcrushersSets, newDejanDayThreeLegRaiseSets, newDejanDayThreePlankSets, newDejanDayThreeCableCrunchSets)
    }

    private fun getReps(): ArrayList<EditText> {
        return arrayListOf<EditText>(newDejanDayThreeDeadliftReps, newDejanDayThreeWideGripPullDownReps, newDejanDayThreeSeatedCableRowReps, newDejanDayThreeHyperExtensionReps, newDejanDayThreeTricepExtensionReps, newDejanDayThreeDipsReps, newDejanDayThreeDumbbellKickbackReps, newDejanDayThreeSkullcrushersReps, newDejanDayThreeLegRaiseReps, newDejanDayThreePlankReps, newDejanDayThreeCableCrunchReps)
    }

    private fun getAmounts(): ArrayList<EditText> {
        return arrayListOf<EditText>(newDejanDayThreeDeadliftAmount, newDejanDayThreeWideGripPullDownAmount, newDejanDayThreeSeatedCableRowAmount, newDejanDayThreeHyperExtensionAmount, newDejanDayThreeTricepExtensionAmount, newDejanDayThreeDipsAmount, newDejanDayThreeDumbbellKickbackAmount, newDejanDayThreeSkullcrushersAmount, newDejanDayThreeCableCrunchAmount)
    }

    override fun getDeadlift(): List<String> {
        return listOf(newDejanDayThreeDeadliftSets.text.toString(), newDejanDayThreeDeadliftReps.text.toString(), newDejanDayThreeDeadliftAmount.text.toString())
    }

    override fun getWideGripPullDown(): List<String> {
        return listOf(newDejanDayThreeWideGripPullDownSets.text.toString(), newDejanDayThreeWideGripPullDownReps.text.toString(), newDejanDayThreeWideGripPullDownAmount.text.toString())
    }

    override fun getSeatedCableRow(): List<String> {
        return listOf(newDejanDayThreeSeatedCableRowSets.text.toString(), newDejanDayThreeSeatedCableRowReps.text.toString(), newDejanDayThreeSeatedCableRowAmount.text.toString())
    }

    override fun getHyperExtension(): List<String> {
        return listOf(newDejanDayThreeHyperExtensionSets.text.toString(), newDejanDayThreeHyperExtensionReps.text.toString(), newDejanDayThreeHyperExtensionAmount.text.toString())
    }

    override fun getTricepExtension(): List<String> {
        return listOf(newDejanDayThreeTricepExtensionSets.text.toString(), newDejanDayThreeTricepExtensionReps.text.toString(), newDejanDayThreeTricepExtensionAmount.text.toString())
    }

    override fun getDips(): List<String> {
        return listOf(newDejanDayThreeDipsSets.text.toString(), newDejanDayThreeDipsReps.text.toString(), newDejanDayThreeDipsAmount.text.toString())
    }

    override fun getDumbbellKickback(): List<String> {
        return listOf(newDejanDayThreeDumbbellKickbackSets.text.toString(), newDejanDayThreeDumbbellKickbackReps.text.toString(), newDejanDayThreeDumbbellKickbackAmount.text.toString())
    }

    override fun getSkullcrushers(): List<String> {
        return listOf(newDejanDayThreeSkullcrushersSets.text.toString(), newDejanDayThreeSkullcrushersReps.text.toString(), newDejanDayThreeSkullcrushersAmount.text.toString())
    }

    override fun getLegRaise(): List<String> {
        return listOf(newDejanDayThreeLegRaiseSets.text.toString(), newDejanDayThreeLegRaiseReps.text.toString())
    }

    override fun getPlank(): List<String> {
        return listOf(newDejanDayThreePlankSets.text.toString(), newDejanDayThreePlankReps.text.toString())
    }

    override fun getCableCrunch(): List<String> {
        return listOf(newDejanDayThreeCableCrunchSets.text.toString(), newDejanDayThreeCableCrunchReps.text.toString(), newDejanDayThreeCableCrunchAmount.text.toString())
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
        return R.layout.layout_new_dejan_day_three
    }
}