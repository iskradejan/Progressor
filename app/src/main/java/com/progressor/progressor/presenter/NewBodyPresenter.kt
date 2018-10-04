package com.progressor.progressor.presenter

import com.progressor.progressor.model.constant.FirebaseConstant
import com.progressor.progressor.model.constant.UserConstant
import com.progressor.progressor.model.dataobjects.account.Body
import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.BodyCalculator
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.service.UserManager
import com.progressor.progressor.view.LoginFragment
import com.progressor.progressor.view.NewBodyFragment
import java.time.LocalDateTime
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
    }

    fun addBody(weight: Double, waist: Double, wrist: Double? = null, hip: Double? = null, forearm: Double? = null, mood: Int) {
        if (view.isFormValid()) {
            val fatPercentage: Double
            if(userManager.user?.person?.gender == UserConstant.PERSON_GENDER_FEMALE) {
                fatPercentage  = bodyCalculator.calculateFemaleFatPercentage(weight = weight, wrist = wrist!!, hip = hip!!, waist = waist, forearm = forearm!!)
            } else {
                fatPercentage = bodyCalculator.calculateMaleFatPercentage(weight = weight, waist = waist)
            }
            val musclePercentage = bodyCalculator.calculateMusclePercentage(fat = fatPercentage)

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
        }
    }

    private fun createBody(weight: Double, fatPercentage: Double, musclePercentage: Double, mood: Int): Body {
        return Body(weight = weight, fatPercentage = fatPercentage, musclePercentage = musclePercentage, mood = mood, createDate = LocalDateTime.now().toString())
    }
}