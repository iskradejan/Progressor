package com.progressor.progressor.presenter

import com.progressor.progressor.model.dataobjects.account.Body
import com.progressor.progressor.model.dataobjects.workout.dejan.DayOne
import com.progressor.progressor.model.dataobjects.workout.dejan.Dejan
import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.service.UserManager
import com.progressor.progressor.view.NewDejanWorkoutFragment
import javax.inject.Inject

class NewDejanWorkoutPresenter @Inject constructor(
        var fragmentNavigator: FragmentNavigator,
        var userManager: UserManager,
        var authenticationManager: AuthenticationManager) {

    private lateinit var view: View

    interface View {
        fun isFormValid(): Boolean
        fun getInclineBenchPress(): Array<String>
        fun getFlatBenchPress(): Array<String>
        fun getChestFlies(): Array<String>
        fun getBicepCurls(): Array<String>
        fun getHammerCurls(): Array<String>
        fun getBarbellRollout(): Array<String>
        fun getFlutterKick(): Array<String>
        fun getStarPlank(): Array<String>
    }

    fun setPresenter(newDejanWorkoutFragment: NewDejanWorkoutFragment) {
        view = newDejanWorkoutFragment
    }

    fun save() {
        if(view.isFormValid()) {
            println("valid valid valid")

            val dayOne = DayOne(
                    inclineChestPress = view.getInclineBenchPress(),
                    flatChestPress = view.getFlatBenchPress(),
                    chestFlies = view.getChestFlies(),
                    bicepCurls = view.getBicepCurls(),
                    hammerCurls = view.getHammerCurls(),
                    barbellRollout = view.getBarbellRollout(),
                    flutterKick = view.getFlutterKick(),
                    starPlank = view.getStarPlank(),
                    createDate = "date"
            )

            if (userManager.user?.workouts == null) {
                val dejanDayOneList: MutableList<DayOne> = ArrayList()
                dejanDayOneList.add(dayOne)
                val dejan = Dejan(dayOne = dejanDayOneList)
                val workoutList: MutableList<Any> = ArrayList()
                workoutList.add(dejan)
                userManager.user?.workouts = workoutList
            } else {
                userManager.user?.workouts?.forEach { workout ->
                    if(workout is Dejan) {
                        workout.dayOne?.let {
                            if(it.isNotEmpty()) {
                                it.add(dayOne)
                            }
                        } ?: run {
                            val dejanDayOneList: MutableList<DayOne> = ArrayList()
                            dejanDayOneList.add(dayOne)
                        }
                    }
                }
            }

            // TODO: Save the user to database and re-visit workouts if dejan exists

            println("valid valid valid")
        } else {
            println("invalid invalid invalid")
        }
    }
}