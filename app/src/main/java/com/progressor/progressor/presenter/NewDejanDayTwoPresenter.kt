package com.progressor.progressor.presenter

import android.content.Context
import com.progressor.progressor.model.constant.FirebaseConstant
import com.progressor.progressor.model.dataobjects.workout.dejan.DayTwo
import com.progressor.progressor.model.dataobjects.workout.dejan.Dejan
import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.service.UserManager
import com.progressor.progressor.view.NewDejanDayTwoFragment
import javax.inject.Inject

class NewDejanDayTwoPresenter @Inject constructor(
        var fragmentNavigator: FragmentNavigator,
        var authenticationManager: AuthenticationManager,
        var userManager: UserManager) {

    private lateinit var view: View
    private var context: Context? = null

    interface View {
        fun isFormValid(): Boolean
        fun getElliptical(): String
    }

    fun setPresenter(newDejanDayTwoFragment: NewDejanDayTwoFragment) {
        view = newDejanDayTwoFragment
        context = newDejanDayTwoFragment.context
    }

    fun save() {
        if (view.isFormValid()) {
            val dayTwo = DayTwo(elliptical = view.getElliptical())

            userManager.user?.workout?.dejan?.dayTwoList?.let {
                it.add(dayTwo)
            } ?: run {
                val dayTwoList: MutableList<DayTwo> = ArrayList()
                dayTwoList.add(dayTwo)
                userManager.user?.workout?.dejan?.dayTwoList = dayTwoList
            }

            authenticationManager.firebaseUser?.uid?.let { uid ->
                userManager.user?.let { user ->
                    userManager.addUser(uid, user, FirebaseConstant.TYPE_DEJAN_DAY_TWO)
                }
            }
        }
    }
}