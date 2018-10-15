package com.progressor.progressor.presenter

import com.progressor.progressor.model.constant.FirebaseConstant
import com.progressor.progressor.model.constant.UserConstant
import com.progressor.progressor.model.dataobjects.account.Body
import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.BodyCalculator
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.service.UserManager
import com.progressor.progressor.view.EmptyDashboardFragment
import com.progressor.progressor.view.LoginFragment
import com.progressor.progressor.view.NewBodyFragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class NewBodyPresenter @Inject constructor(private var fragmentNavigator: FragmentNavigator, private var userManager: UserManager, private var authenticationManager: AuthenticationManager) {
    private lateinit var view: View
    private var bodyCalculator = BodyCalculator()

    interface View {
        fun isFormValid(): Boolean
    }

    fun setPresenter(newBodyFragment: NewBodyFragment) {
        view = newBodyFragment
        initialize()
    }

    fun initialize() {
        if (!authenticationManager.isLoggedIn()) {
            fragmentNavigator.navigate(LoginFragment())
        }

        userManager.user?.bodyHistory?.last()?.let {
            if(!isEligible(it.createDate)) {
                // TODO: Change this to some other dashboard screen
                fragmentNavigator.navigate(EmptyDashboardFragment())
            }
        }
    }

    private fun isEligible(localDateTime: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val formatDateTime = LocalDateTime.parse(localDateTime, formatter)
        return formatDateTime.isAfter(LocalDateTime.now().plusMonths(1))
    }

    fun addBody(weight: Double, waist: Double, wrist: Double? = null, hip: Double? = null, forearm: Double? = null, mood: Int): Boolean {
        if (view.isFormValid()) {
            val fatPercentage: Double
            if (userManager.user?.person?.gender == UserConstant.PERSON_GENDER_FEMALE) {
                fatPercentage = bodyCalculator.calculateFemaleFatPercentage(weight = weight, wrist = wrist!!, hip = hip!!, waist = waist, forearm = forearm!!)
            } else {
                fatPercentage = bodyCalculator.calculateMaleFatPercentage(weight = weight, waist = waist)
            }
            val musclePercentage = bodyCalculator.calculateMusclePercentage(fat = fatPercentage)

            if (fatPercentage < 4 || (fatPercentage.plus(musclePercentage)).plus(15) > 100) {
                return false
            }

            val body = createBody(weight = weight, fatPercentage = fatPercentage, musclePercentage = musclePercentage, mood = mood)

            if (userManager.user?.bodyHistory == null) {
                val bodyHistory: MutableList<Body> = ArrayList()
                bodyHistory.add(body)
                userManager.user?.bodyHistory = bodyHistory
            } else {
                userManager.user?.bodyHistory?.add(body)
            }

            authenticationManager.firebaseUser?.uid?.let { uid ->
                userManager.user?.let { user ->
                    userManager.addUser(uid, user, FirebaseConstant.TYPE_NEW_BODY)
                }
            }

            return true
        }
        return false
    }

    private fun createBody(weight: Double, fatPercentage: Double, musclePercentage: Double, mood: Int): Body {
        return Body(weight = weight, fatPercentage = Math.round(fatPercentage).toInt(), musclePercentage = Math.round(musclePercentage).toInt(), mood = mood, createDate = LocalDateTime.now().toString())
    }
}