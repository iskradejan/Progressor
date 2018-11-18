package com.progressor.progressor.presenter

import android.content.Context
import com.progressor.progressor.model.constant.FirebaseConstant
import com.progressor.progressor.model.dataobjects.workout.dejan.DayThree
import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.service.UserManager
import com.progressor.progressor.view.NewDejanDayThreeFragment
import java.time.LocalDateTime
import javax.inject.Inject

class NewDejanDayThreePresenter @Inject constructor(
        var fragmentNavigator: FragmentNavigator,
        var authenticationManager: AuthenticationManager,
        var userManager: UserManager) {

    private lateinit var view: View
    private var context: Context? = null

    interface View {
        fun isFormValid(): Boolean
        fun getDeadlift(): List<String>
        fun getWideGripPullDown(): List<String>
        fun getSeatedCableRow(): List<String>
        fun getHyperExtension(): List<String>
        fun getTricepExtension(): List<String>
        fun getDips(): List<String>
        fun getDumbbellKickback(): List<String>
        fun getSkullcrushers(): List<String>
        fun getLegRaise(): List<String>
        fun getPlank(): List<String>
        fun getCableCrunch(): List<String>
    }

    fun setPresenter(newDejanDayThreeFragment: NewDejanDayThreeFragment) {
        view = newDejanDayThreeFragment
        context = newDejanDayThreeFragment.context
    }

    fun save() {
        if (view.isFormValid()) {
            val dayThree = DayThree(
                    deadlift = view.getDeadlift(),
                    skullcrushers = view.getSkullcrushers(),
                    dumbbellKickback = view.getDumbbellKickback(),
                    dips = view.getDips(),
                    tricepExtension = view.getTricepExtension(),
                    cableCrunch = view.getCableCrunch(),
                    plank = view.getPlank(),
                    legRaise = view.getLegRaise(),
                    hyperExtension = view.getHyperExtension(),
                    seatedCableRow = view.getSeatedCableRow(),
                    wideGripPullDown = view.getWideGripPullDown(),
                    createDate = LocalDateTime.now().toString()
            )

            userManager.user?.workout?.dejan?.dayThreeList?.let {
                it.add(dayThree)
            } ?: run {
                val dayThreeList: MutableList<DayThree> = ArrayList()
                dayThreeList.add(dayThree)
                userManager.user?.workout?.dejan?.dayThreeList = dayThreeList
            }

            authenticationManager.firebaseUser?.uid?.let { uid ->
                userManager.user?.let { user ->
                    userManager.addUser(uid, user, FirebaseConstant.TYPE_DEJAN_DAY_THREE)
                }
            }
        }
    }

    fun hasHistory(): Boolean {
        if(userManager.user?.workout?.dejan?.dayThreeList == null) {
            return false
        }

        userManager.user?.workout?.dejan?.dayThreeList?.let {
            return it.isNotEmpty()
        }

        return true
    }
}