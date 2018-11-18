package com.progressor.progressor.view

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.model.constant.FirebaseConstant
import com.progressor.progressor.model.dataobjects.helper.FirebaseResponse
import com.progressor.progressor.presenter.NewDejanDayOnePresenter
import com.progressor.progressor.service.RxBus
import com.progressor.progressor.service.dateTimeStampToDisplay
import kotlinx.android.synthetic.main.layout_day_one_history.*
import kotlinx.android.synthetic.main.layout_new_dejan_day_one.*
import javax.inject.Inject
import java.util.*


class NewDejanDayOneFragment : BaseFragment(), NewDejanDayOnePresenter.View {
    @Inject
    lateinit var presenter: NewDejanDayOnePresenter
    lateinit var defaultLabelColor: ColorStateList

    lateinit var currentView: View

    private fun initialize() {
        RxBus.subscribe<FirebaseResponse>(this) {
            if (it.getType().equals(FirebaseConstant.TYPE_DEJAN_DAY_ONE)) {
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
            newDejanOneHistory.visibility = View.VISIBLE
            newDejanOneHistory.setOnClickListener {
                context?.let {
                    val inflater = layoutInflater
                    val alertLayout = inflater.inflate(R.layout.layout_day_one_history, null)
                    val lastEntry = userManager.user?.workout?.dejan?.dayOneList?.last()
                    val alert = AlertDialog.Builder(context)
                    alert.setView(alertLayout)
                    val dialog = alert.create()

                    dialog.show()

                    dialog.dejanOneHistoryBenchPressSets.text = lastEntry?.inclineChestPress?.get(0) ?: "--"
                    dialog.dejanOneHistoryBenchPressReps.text = lastEntry?.inclineChestPress?.get(1) ?: "--"
                    dialog.dejanOneHistoryBenchPressAmount.text = lastEntry?.inclineChestPress?.get(2) ?: "--"

                    dialog.dejanOneHistoryFlatSets.text = lastEntry?.flatChestPress?.get(0) ?: "--"
                    dialog.dejanOneHistoryFlatReps.text = lastEntry?.flatChestPress?.get(1) ?: "--"
                    dialog.dejanOneHistoryFlatAmount.text = lastEntry?.flatChestPress?.get(2) ?: "--"

                    dialog.dejanOneHistoryFliesSets.text = lastEntry?.chestFlies?.get(0) ?: "--"
                    dialog.dejanOneHistoryFliesReps.text = lastEntry?.chestFlies?.get(1) ?: "--"
                    dialog.dejanOneHistoryFliesAmount.text = lastEntry?.chestFlies?.get(2) ?: "--"

                    dialog.dejanOneHistoryBicepCurlsSets.text = lastEntry?.bicepCurls?.get(0) ?: "--"
                    dialog.dejanOneHistoryBicepCurlsReps.text = lastEntry?.bicepCurls?.get(1) ?: "--"
                    dialog.dejanOneHistoryBicepCurlsAmount.text = lastEntry?.bicepCurls?.get(2) ?: "--"

                    dialog.dejanOneHistoryHammerCurlsSets.text = lastEntry?.hammerCurls?.get(0) ?: "--"
                    dialog.dejanOneHistoryHammerCurlsReps.text = lastEntry?.hammerCurls?.get(1) ?: "--"
                    dialog.dejanOneHistoryHammerCurlsAmount.text = lastEntry?.hammerCurls?.get(2) ?: "--"

                    dialog.dejanOneHistoryBarbellRolloutSets.text = lastEntry?.barbellRollout?.get(0) ?: "--"
                    dialog.dejanOneHistoryBarbellRolloutReps.text = lastEntry?.barbellRollout?.get(1) ?: "--"

                    dialog.dejanOneHistoryFlutterKickSets.text = lastEntry?.flutterKick?.get(0) ?: "--"
                    dialog.dejanOneHistoryFlutterKickReps.text = lastEntry?.flutterKick?.get(1) ?: "--"

                    dialog.dejanOneHistoryStarPlankSets.text = lastEntry?.starPlank?.get(0) ?: "--"
                    dialog.dejanOneHistoryStarPlankReps.text = lastEntry?.starPlank?.get(1) ?: "--"

                    lastEntry?.let {
                        dialog.dejanOneHistoryDate.text = dateTimeStampToDisplay(it.createDate)
                    }
                }
            }
        }

        defaultLabelColor = newDejanWorkoutInclineLabel.textColors

        newDejanWorkoutDayTwo.setOnClickListener {
            fragmentNavigator.to(NewDejanDayTwoFragment())
        }
        newDejanWorkoutDayThree.setOnClickListener {
            fragmentNavigator.to(NewDejanDayThreeFragment())
        }
        newDejanWorkoutDayFour.setOnClickListener {
            fragmentNavigator.to(NewDejanDayFourFragment())
        }
        newDejanWorkoutDayFive.setOnClickListener {
            fragmentNavigator.to(NewDejanDayFiveFragment())
        }
        newDejanWorkoutSaveButton.setOnClickListener {
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
        return arrayListOf<EditText>(newDejanWorkoutInclineSets, newDejanWorkoutFlatSets, newDejanWorkoutFliesSets, newDejanWorkoutBicepCurlsSets, newDejanWorkoutHammerCurlsSets, newDejanWorkoutBarbellCurlsSets, newDejanWorkoutFlutterSets, newDejanWorkoutStarSets)
    }

    private fun getReps(): ArrayList<EditText> {
        return arrayListOf<EditText>(newDejanWorkoutInclineReps, newDejanWorkoutFlatReps, newDejanWorkoutFliesReps, newDejanWorkoutBicepCurlsReps, newDejanWorkoutHammerCurlsReps, newDejanWorkoutBarbellCurlsReps, newDejanWorkoutFlutterReps, newDejanWorkoutStarReps)
    }

    private fun getAmounts(): ArrayList<EditText> {
        return arrayListOf<EditText>(newDejanWorkoutInclineAmount, newDejanWorkoutFlatAmount, newDejanWorkoutFliesAmount, newDejanWorkoutBicepCurlsAmount, newDejanWorkoutHammerCurlsAmount)
    }

    override fun getInclineBenchPress(): List<String> {
        return listOf(newDejanWorkoutInclineSets.text.toString(), newDejanWorkoutInclineReps.text.toString(), newDejanWorkoutInclineAmount.text.toString())
    }

    override fun getFlatBenchPress(): List<String> {
        return listOf(newDejanWorkoutFlatSets.text.toString(), newDejanWorkoutFlatReps.text.toString(), newDejanWorkoutFlatAmount.text.toString())
    }

    override fun getChestFlies(): List<String> {
        return listOf(newDejanWorkoutFliesSets.text.toString(), newDejanWorkoutFliesReps.text.toString(), newDejanWorkoutFliesAmount.text.toString())
    }

    override fun getBicepCurls(): List<String> {
        return listOf(newDejanWorkoutBicepCurlsSets.text.toString(), newDejanWorkoutBicepCurlsReps.text.toString(), newDejanWorkoutBicepCurlsAmount.text.toString())
    }

    override fun getHammerCurls(): List<String> {
        return listOf(newDejanWorkoutHammerCurlsSets.text.toString(), newDejanWorkoutHammerCurlsReps.text.toString(), newDejanWorkoutHammerCurlsAmount.text.toString())
    }

    override fun getBarbellRollout(): List<String> {
        return listOf(newDejanWorkoutBarbellCurlsSets.text.toString(), newDejanWorkoutBarbellCurlsReps.text.toString())
    }

    override fun getFlutterKick(): List<String> {
        return listOf(newDejanWorkoutFlutterSets.text.toString(), newDejanWorkoutFlutterReps.text.toString())
    }

    override fun getStarPlank(): List<String> {
        return listOf(newDejanWorkoutStarSets.text.toString(), newDejanWorkoutStarReps.text.toString())
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
        return R.layout.layout_new_dejan_day_one
    }
}