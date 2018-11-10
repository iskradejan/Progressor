package com.progressor.progressor.presenter

import com.progressor.progressor.model.constant.FirebaseConstant
import com.progressor.progressor.model.constant.UserConstant
import com.progressor.progressor.model.dataobjects.workout.dejan.DayOne
import com.progressor.progressor.model.dataobjects.workout.dejan.Dejan
import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.service.UserManager
import com.progressor.progressor.view.NewDejanDayOneFragment
import javax.inject.Inject

class NewDejanDayOnePresenter @Inject constructor(
        var fragmentNavigator: FragmentNavigator,
        var userManager: UserManager,
        var authenticationManager: AuthenticationManager) {

    private lateinit var view: View

    interface View {
        fun isFormValid(): Boolean
        fun getInclineBenchPress(): List<String>
        fun getFlatBenchPress(): List<String>
        fun getChestFlies(): List<String>
        fun getBicepCurls(): List<String>
        fun getHammerCurls(): List<String>
        fun getBarbellRollout(): List<String>
        fun getFlutterKick(): List<String>
        fun getStarPlank(): List<String>
    }

    fun setPresenter(newDejanDayOneFragment: NewDejanDayOneFragment) {
        view = newDejanDayOneFragment
    }

    fun save() {
        if (view.isFormValid()) {
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

            userManager.user?.workout?.dejan?.dayOneList?.let {
                it.add(dayOne)
            } ?: run {
                val dayOneList: MutableList<DayOne> = ArrayList()
                dayOneList.add(dayOne)
                userManager.user?.workout?.dejan?.dayOneList = dayOneList
            }

            authenticationManager.firebaseUser?.uid?.let { uid ->
                userManager.user?.let { user ->
                    userManager.addUser(uid, user, FirebaseConstant.TYPE_DEJAN_DAY_ONE)
                }
            }
            println("valid valid valid")
        }
    }
}