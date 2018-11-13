package com.progressor.progressor.view

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
import kotlinx.android.synthetic.main.layout_new_dejan_day_one.*
import javax.inject.Inject

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