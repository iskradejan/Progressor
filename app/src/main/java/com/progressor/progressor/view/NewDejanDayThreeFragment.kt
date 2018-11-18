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
import com.progressor.progressor.presenter.NewDejanDayThreePresenter
import com.progressor.progressor.service.RxBus
import com.progressor.progressor.service.dateTimeStampToDisplay
import kotlinx.android.synthetic.main.layout_day_three_history.*
import kotlinx.android.synthetic.main.layout_new_dejan_day_three.*
import java.util.*
import javax.inject.Inject

class NewDejanDayThreeFragment : BaseFragment(), NewDejanDayThreePresenter.View {
    @Inject
    lateinit var presenter: NewDejanDayThreePresenter
    lateinit var defaultLabelColor: ColorStateList
    lateinit var currentView: View

    private fun initialize() {
        (activity as MainActivity).setBackFragment(DashboardFragment())

        RxBus.subscribe<FirebaseResponse>(this) {
            if (it.getType().equals(FirebaseConstant.TYPE_DEJAN_DAY_THREE)) {
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
            newDejanThreeHistory.visibility = View.VISIBLE
            newDejanThreeHistory.setOnClickListener {
                context?.let {
                    val inflater = layoutInflater
                    val alertLayout = inflater.inflate(R.layout.layout_day_three_history, null)
                    val lastEntry = userManager.user?.workout?.dejan?.dayThreeList?.last()
                    val alert = AlertDialog.Builder(context)
                    alert.setView(alertLayout)
                    val dialog = alert.create()

                    dialog.show()

                    dialog.dejanThreeHistoryDeadliftSets.text = lastEntry?.deadlift?.get(0) ?: "--"
                    dialog.dejanThreeHistoryDeadliftReps.text = lastEntry?.deadlift?.get(1) ?: "--"
                    dialog.dejanThreeHistoryDeadliftAmount.text = lastEntry?.deadlift?.get(2) ?: "--"

                    dialog.dejanThreeHistoryWideGripPullDownSets.text = lastEntry?.wideGripPullDown?.get(0) ?: "--"
                    dialog.dejanThreeHistoryWideGripPullDownReps.text = lastEntry?.wideGripPullDown?.get(1) ?: "--"
                    dialog.dejanThreeHistoryWideGripPullDownAmount.text = lastEntry?.wideGripPullDown?.get(2) ?: "--"

                    dialog.dejanThreeHistorySeatedCableRowsSets.text = lastEntry?.seatedCableRow?.get(0) ?: "--"
                    dialog.dejanThreeHistorySeatedCableRowsReps.text = lastEntry?.seatedCableRow?.get(1) ?: "--"
                    dialog.dejanThreeHistorySeatedCableRowsAmount.text = lastEntry?.seatedCableRow?.get(2) ?: "--"

                    dialog.dejanThreeHistoryHyperExtensionSets.text = lastEntry?.hyperExtension?.get(0) ?: "--"
                    dialog.dejanThreeHistoryHyperExtensionReps.text = lastEntry?.hyperExtension?.get(1) ?: "--"
                    dialog.dejanThreeHistoryHyperExtensionAmount.text = lastEntry?.hyperExtension?.get(2) ?: "--"

                    dialog.dejanThreeHistoryTricepExtensionSets.text = lastEntry?.tricepExtension?.get(0) ?: "--"
                    dialog.dejanThreeHistoryTricepExtensionReps.text = lastEntry?.tricepExtension?.get(1) ?: "--"
                    dialog.dejanThreeHistoryTricepExtensionAmount.text = lastEntry?.tricepExtension?.get(2) ?: "--"

                    dialog.dejanThreeHistoryDipSets.text = lastEntry?.dips?.get(0) ?: "--"
                    dialog.dejanThreeHistoryDipReps.text = lastEntry?.dips?.get(1) ?: "--"
                    dialog.dejanThreeHistoryDipAmount.text = lastEntry?.dips?.get(2) ?: "--"

                    dialog.dejanThreeHistoryDumbbellKickbackSets.text = lastEntry?.dumbbellKickback?.get(0) ?: "--"
                    dialog.dejanThreeHistoryDumbbellKickbackReps.text = lastEntry?.dumbbellKickback?.get(1) ?: "--"
                    dialog.dejanThreeHistoryDumbbellKickbackAmount.text = lastEntry?.dumbbellKickback?.get(2) ?: "--"

                    dialog.dejanThreeHistorySkullCrushersSets.text = lastEntry?.skullcrushers?.get(0) ?: "--"
                    dialog.dejanThreeHistorySkullCrushersReps.text = lastEntry?.skullcrushers?.get(1) ?: "--"
                    dialog.dejanThreeHistorySkullCrushersAmount.text = lastEntry?.skullcrushers?.get(2) ?: "--"

                    dialog.dejanThreeHistoryLegRaiseSets.text = lastEntry?.legRaise?.get(0) ?: "--"
                    dialog.dejanThreeHistoryLegRaiseReps.text = lastEntry?.legRaise?.get(1) ?: "--"

                    dialog.dejanThreeHistoryPlankSets.text = lastEntry?.plank?.get(0) ?: "--"
                    dialog.dejanThreeHistoryPlankReps.text = lastEntry?.plank?.get(1) ?: "--"

                    dialog.dejanThreeHistoryCableCrunchSets.text = lastEntry?.cableCrunch?.get(0) ?: "--"
                    dialog.dejanThreeHistoryCableCrunchReps.text = lastEntry?.cableCrunch?.get(1) ?: "--"
                    dialog.dejanThreeHistoryCableCrunchAmount.text = lastEntry?.cableCrunch?.get(2) ?: "--"

                    lastEntry?.let {
                        dialog.dejanThreeHistoryDate.text = dateTimeStampToDisplay(it.createDate)
                    }
                }
            }
        }

        defaultLabelColor = newDejanDayThreeDeadliftLabel.textColors

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
        return R.layout.layout_new_dejan_day_three
    }
}