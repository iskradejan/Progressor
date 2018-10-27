package com.progressor.progressor.presenter

import android.content.Context
import com.progressor.progressor.model.constant.FirebaseConstant
import com.progressor.progressor.model.dataobjects.account.User
import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.service.UserManager
import com.progressor.progressor.view.ProfileCreateFragment
import javax.inject.Inject

class ProfileCreatePresenter @Inject constructor(
        var fragmentNavigator: FragmentNavigator,
        var authenticationManager: AuthenticationManager,
        var userManager: UserManager) {

    private lateinit var view: View
    private var context: Context? = null

    interface View {
        fun isFormValid(): Boolean
    }

    fun setPresenter(profileCreateFragment: ProfileCreateFragment) {
        view = profileCreateFragment
        context = profileCreateFragment.context
    }

    fun createProfile(gender: Int, height: Int, weight: Int, dob: String) {
        if (view.isFormValid()) {
            authenticationManager.firebaseUser?.uid?.let { it ->
                val person = userManager.createPerson(gender, dob, height, weight)
                userManager.addUser(it, User(person = person), FirebaseConstant.TYPE_CREATE_PROFILE)
            }
        }
    }
}