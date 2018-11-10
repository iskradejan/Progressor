package com.progressor.progressor.view

import android.os.Bundle
import android.view.View
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.presenter.NewDejanWorkoutPresenter
import com.progressor.progressor.service.DejanCreator
import com.progressor.progressor.service.ExerciseCreator
import kotlinx.android.synthetic.main.layout_new_dejan_workout.*
import javax.inject.Inject

class NewDejanWorkoutFragment : BaseFragment(), NewDejanWorkoutPresenter.View {
    @Inject
    lateinit var presenter: NewDejanWorkoutPresenter
    private var exerciseCreator = ExerciseCreator()
    private var dejanCreator = DejanCreator()

    lateinit var currentView: View

    private fun initialize() {
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

//        val dejan = Dejan(
//            dayOne = dejanCreator.createDayOne(
//                    inclineChestPress = exerciseCreator.createWeightExercise(3, 12, 135),
//                    flatChestPress = exerciseCreator.createWeightExercise(3, 12, 140),
//                    chestFlies = exerciseCreator.createWeightExercise(3, 12, 155),
//                    bicepCurls = exerciseCreator.createWeightExercise(3, 15, 10),
//                    hammerCurls = exerciseCreator.createWeightExercise(3, 12, 10),
//                    barbellRollout = exerciseCreator.createBodyExercise(3, 20),
//                    flutterKick = exerciseCreator.createBodyExercise(3, 20),
//                    starPlank = exerciseCreator.createBodyExercise(3, 60)
//            ),
//            dayTwo = dejanCreator.createDayTwo(
//                    elliptical = 60
//            ),
//            dayThree = dejanCreator.createDayThree(
//                    deadlift = exerciseCreator.createWeightExercise(3, 10, 320),
//                    wideGripPullDown = exerciseCreator.createWeightExercise(3, 12, 120),
//                    seatedCableRow = exerciseCreator.createWeightExercise(3, 12, 140),
//                    hyperExtension = exerciseCreator.createWeightExercise(3, 10, 185),
//                    tricepExtension = exerciseCreator.createWeightExercise(3, 12, 140),
//                    dips = exerciseCreator.createWeightExercise(3, 12, 160),
//                    dumbbellKickback = exerciseCreator.createWeightExercise(3, 10, 100),
//                    skullcrushers = exerciseCreator.createWeightExercise(3, 10, 100),
//                    legRaise = exerciseCreator.createBodyExercise(3, 20),
//                    plank = exerciseCreator.createBodyExercise(3, 60),
//                    cableCrunch = exerciseCreator.createWeightExercise(3, 15, 90)
//            ),
//            dayFour = dejanCreator.createDayFour(
//                    arcTrainer = 60
//            ),
//            dayFive = dejanCreator.createDayFive(
//                    squats = exerciseCreator.createWeightExercise(3, 12, 390),
//                    romanianDeadlift = exerciseCreator.createWeightExercise(3, 12, 300),
//                    pistolSquats = exerciseCreator.createWeightExercise(3, 10, 250),
//                    lunges = exerciseCreator.createWeightExercise(3, 12, 180),
//                    cableFrontRaise = exerciseCreator.createWeightExercise(3, 12, 60),
//                    pushPress = exerciseCreator.createWeightExercise(3, 12, 100),
//                    dumbbellLateralRaise = exerciseCreator.createWeightExercise(3, 12, 100),
//                    hangingLegRaise = exerciseCreator.createBodyExercise(3, 20),
//                    bicycles = exerciseCreator.createBodyExercise(3, 20),
//                    russianTwists = exerciseCreator.createBodyExercise(3, 20)
//            )
//        )
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
        return R.layout.layout_new_dejan_workout
    }
}