package com.progressor.progressor.presenter

import com.progressor.progressor.model.constant.UserConstant
import com.progressor.progressor.model.dataobjects.workout.dejan.Dejan
import com.progressor.progressor.model.dataobjects.workout.dejan.Workout
import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.service.UserManager
import com.progressor.progressor.view.DejanWorkoutFragment
import javax.inject.Inject

class DejanWorkoutPresenter @Inject constructor(
        var fragmentNavigator: FragmentNavigator,
        var userManager: UserManager,
        var authenticationManager: AuthenticationManager) {

    private lateinit var view: View

    interface View {}

    fun setPresenter(dejanWorkoutFragment: DejanWorkoutFragment) {
        view = dejanWorkoutFragment
    }

    fun manageWorkout() {
        if (userManager.user?.workout == null) {
            userManager.user?.workout = Workout(Dejan(), active = UserConstant.WORKOUT_DEJAN)
        } else if(userManager.user?.workout?.dejan == null) {
            userManager.user?.workout?.dejan = Dejan()
        }
    }
}