package com.progressor.progressor.view

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.progressor.progressor.MainActivity
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.model.constant.FirebaseConstant
import com.progressor.progressor.model.dataobjects.helper.FirebaseResponse
import com.progressor.progressor.presenter.NewDejanDayFivePresenter
import com.progressor.progressor.service.RxBus
import com.progressor.progressor.service.dateTimeStampToDisplay
import kotlinx.android.synthetic.main.layout_day_five_history.*
import kotlinx.android.synthetic.main.layout_new_dejan_day_five.*
import javax.inject.Inject

class NewDejanDayFiveFragment : BaseFragment(), NewDejanDayFivePresenter.View {
    @Inject
    lateinit var presenter: NewDejanDayFivePresenter
    lateinit var defaultLabelColor: ColorStateList
    lateinit var currentView: View

    private fun initialize() {
        (activity as MainActivity).setBackFragment(DashboardFragment())

        RxBus.subscribe<FirebaseResponse>(this) {
            if (it.getType().equals(FirebaseConstant.TYPE_DEJAN_DAY_FIVE)) {
                when (it.getSuccess()) {
                    true -> {
                        context.let { context ->
                            Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
                            resetForm()
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

        if(presenter.hasHistory()) {
            newDejanFiveHistory.visibility = View.VISIBLE
            newDejanFiveHistory.setOnClickListener {
                context?.let {
                    val inflater = layoutInflater
                    val alertLayout = inflater.inflate(R.layout.layout_day_five_history, null)
                    val lastEntry = userManager.user?.workout?.dejan?.dayFiveList?.last()
                    val alert = AlertDialog.Builder(context)
                    alert.setView(alertLayout)
                    val dialog = alert.create()

                    dialog.show()

                    dialog.dejanFiveHistorySquatSets.text = lastEntry?.squats?.get(0) ?: "--"
                    dialog.dejanFiveHistorySquatReps.text = lastEntry?.squats?.get(1) ?: "--"
                    dialog.dejanFiveHistorySquatAmount.text = lastEntry?.squats?.get(2) ?: "--"

                    dialog.dejanFiveHistoryRomanianDeadliftSets.text = lastEntry?.romanianDeadlift?.get(0) ?: "--"
                    dialog.dejanFiveHistoryRomanianDeadliftReps.text = lastEntry?.romanianDeadlift?.get(1) ?: "--"
                    dialog.dejanFiveHistoryRomanianDeadliftAmount.text = lastEntry?.romanianDeadlift?.get(2) ?: "--"

                    dialog.dejanFiveHistoryPistolSquatSets.text = lastEntry?.pistolSquats?.get(0) ?: "--"
                    dialog.dejanFiveHistoryPistolSquatReps.text = lastEntry?.pistolSquats?.get(1) ?: "--"
                    dialog.dejanFiveHistoryPistolSquatAmount.text = lastEntry?.pistolSquats?.get(2) ?: "--"

                    dialog.dejanFiveHistoryLungesSets.text = lastEntry?.lunges?.get(0) ?: "--"
                    dialog.dejanFiveHistoryLungesReps.text = lastEntry?.lunges?.get(1) ?: "--"
                    dialog.dejanFiveHistoryLungesAmount.text = lastEntry?.lunges?.get(2) ?: "--"

                    dialog.dejanFiveHistoryCableFrontRaiseSets.text = lastEntry?.cableFrontRaise?.get(0) ?: "--"
                    dialog.dejanFiveHistoryCableFrontRaiseReps.text = lastEntry?.cableFrontRaise?.get(1) ?: "--"
                    dialog.dejanFiveHistoryCableFrontRaiseAmount.text = lastEntry?.cableFrontRaise?.get(2) ?: "--"

                    dialog.dejanFiveHistoryPushPressSets.text = lastEntry?.pushPress?.get(0) ?: "--"
                    dialog.dejanFiveHistoryPushPressReps.text = lastEntry?.pushPress?.get(1) ?: "--"
                    dialog.dejanFiveHistoryPushPressAmount.text = lastEntry?.pushPress?.get(2) ?: "--"

                    dialog.dejanFiveHistoryDumbbellLateralRaiseSets.text = lastEntry?.dumbbellLateralRaise?.get(0) ?: "--"
                    dialog.dejanFiveHistoryDumbbellLateralRaiseReps.text = lastEntry?.dumbbellLateralRaise?.get(1) ?: "--"
                    dialog.dejanFiveHistoryDumbbellLateralRaiseAmount.text = lastEntry?.dumbbellLateralRaise?.get(2) ?: "--"

                    dialog.dejanFiveHistoryHangingLegRaiseSets.text = lastEntry?.hangingLegRaise?.get(0) ?: "--"
                    dialog.dejanFiveHistoryHangingLegRaiseReps.text = lastEntry?.hangingLegRaise?.get(1) ?: "--"

                    dialog.dejanFiveHistoryBicyclesSets.text = lastEntry?.bicycles?.get(0) ?: "--"
                    dialog.dejanFiveHistoryBicyclesReps.text = lastEntry?.bicycles?.get(1) ?: "--"

                    dialog.dejanFiveHistoryRussianTwistSets.text = lastEntry?.russianTwists?.get(0) ?: "--"
                    dialog.dejanFiveHistoryRussianTwistReps.text = lastEntry?.russianTwists?.get(1) ?: "--"

                    lastEntry?.let {
                        dialog.dejanFiveHistoryDate.text = dateTimeStampToDisplay(it.createDate)
                    }
                }
            }
        }

        defaultLabelColor = newDejanDayFiveSquatsLabel.textColors

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

    private fun resetForm() {
        for(set in getSets()) {
            set.setText("")
        }

        for(rep in getReps()) {
            rep.setText("")
        }

        for(amount in getAmounts()) {
            amount.setText("")
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
        return R.layout.layout_new_dejan_day_five
    }
}