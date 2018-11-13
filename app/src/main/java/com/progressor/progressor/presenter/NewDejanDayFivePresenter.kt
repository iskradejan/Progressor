package com.progressor.progressor.presenter

import android.content.Context
import com.progressor.progressor.model.constant.FirebaseConstant
import com.progressor.progressor.model.dataobjects.workout.dejan.DayFive
import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.service.UserManager
import com.progressor.progressor.view.NewDejanDayFiveFragment
import java.time.LocalDateTime
import javax.inject.Inject

class NewDejanDayFivePresenter @Inject constructor(
        var fragmentNavigator: FragmentNavigator,
        var authenticationManager: AuthenticationManager,
        var userManager: UserManager) {

    private lateinit var view: View
    private var context: Context? = null

    interface View {
        fun isFormValid(): Boolean
        fun getSquats(): List<String>
        fun getRomanianDeadlifts(): List<String>
        fun getPistolSquats(): List<String>
        fun getLunges(): List<String>
        fun getCableFrontRaise(): List<String>
        fun getPushPress(): List<String>
        fun getDumbbellLateralRaise(): List<String>
        fun getHangingLegRaise(): List<String>
        fun getBicycle(): List<String>
        fun getRussianTwists(): List<String>
    }

    fun setPresenter(newDejanDayFiveFragment: NewDejanDayFiveFragment) {
        view = newDejanDayFiveFragment
        context = newDejanDayFiveFragment.context
    }

    fun save() {
        if (view.isFormValid()) {
            val dayFive = DayFive(
                    squats = view.getSquats(),
                    russianTwists = view.getRussianTwists(),
                    bicycles = view.getBicycle(),
                    hangingLegRaise = view.getHangingLegRaise(),
                    dumbbellLateralRaise = view.getDumbbellLateralRaise(),
                    pushPress = view.getPushPress(),
                    cableFrontRaise = view.getCableFrontRaise(),
                    lunges = view.getLunges(),
                    pistolSquats = view.getPistolSquats(),
                    romanianDeadlift = view.getRomanianDeadlifts(),
                    createDate = LocalDateTime.now().toString()
            )

            userManager.user?.workout?.dejan?.dayFiveList?.let {
                it.add(dayFive)
            } ?: run {
                val dayFiveList: MutableList<DayFive> = ArrayList()
                dayFiveList.add(dayFive)
                userManager.user?.workout?.dejan?.dayFiveList = dayFiveList
            }

            authenticationManager.firebaseUser?.uid?.let { uid ->
                userManager.user?.let { user ->
                    userManager.addUser(uid, user, FirebaseConstant.TYPE_DEJAN_DAY_FIVE)
                }
            }
        }
    }
}