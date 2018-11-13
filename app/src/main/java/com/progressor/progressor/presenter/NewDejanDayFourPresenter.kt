package com.progressor.progressor.presenter

import android.content.Context
import com.progressor.progressor.model.constant.FirebaseConstant
import com.progressor.progressor.model.dataobjects.workout.dejan.DayFour
import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.service.UserManager
import com.progressor.progressor.view.NewDejanDayFourFragment
import javax.inject.Inject

class NewDejanDayFourPresenter @Inject constructor(
        var fragmentNavigator: FragmentNavigator,
        var authenticationManager: AuthenticationManager,
        var userManager: UserManager) {

    private lateinit var view: View
    private var context: Context? = null

    interface View {
        fun isFormValid(): Boolean
        fun getArcTrainer(): String
    }

    fun setPresenter(newDejanDayFourFragment: NewDejanDayFourFragment) {
        view = newDejanDayFourFragment
        context = newDejanDayFourFragment.context
    }

    fun save() {
        if (view.isFormValid()) {
            val dayFour = DayFour(arcTrainer = view.getArcTrainer())

            userManager.user?.workout?.dejan?.dayFourList?.let {
                it.add(dayFour)
            } ?: run {
                val dayFourList: MutableList<DayFour> = ArrayList()
                dayFourList.add(dayFour)
                userManager.user?.workout?.dejan?.dayFourList = dayFourList
            }

            authenticationManager.firebaseUser?.uid?.let { uid ->
                userManager.user?.let { user ->
                    userManager.addUser(uid, user, FirebaseConstant.TYPE_DEJAN_DAY_FOUR)
                }
            }
        }
    }
}