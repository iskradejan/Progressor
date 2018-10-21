package com.progressor.progressor.service

import android.app.Activity
import android.content.Context
import com.google.firebase.auth.*
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.model.constant.FirebaseConstant
import com.progressor.progressor.model.dataobjects.helper.FirebaseResponse
import com.progressor.progressor.view.EmptyDashboardFragment
import javax.inject.Inject
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.FirebaseUser
import com.progressor.progressor.view.EmailVerifyFragment

class AuthenticationManager constructor(private val mainActivity: Activity) {
    @Inject
    lateinit var fragmentNavigator: FragmentNavigator
    @Inject
    lateinit var userManager: UserManager

    var firebaseAuth: FirebaseAuth? = null
    var firebaseUser: FirebaseUser? = null
    var authenticated: Boolean = false
    var verified: Boolean = false

    init {
        (mainActivity as MainComponentInterface).mainComponent?.inject(this)
    }

    fun createAccount(context: Context, email: String, password: String, displayName: String) {
        if (isLoggedIn() && isVerified()) {
            fragmentNavigator.to(EmptyDashboardFragment())
        } else if (isLoggedIn() && !isVerified()) {
            fragmentNavigator.to(EmailVerifyFragment())
        }

        val response = FirebaseResponse()
        response.setType(FirebaseConstant.TYPE_CREATE_ACCOUNT)

        firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener(context as Activity) { task ->
            if (task.isSuccessful) {
                firebaseUser = firebaseAuth?.currentUser
                authenticated = true

                val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(displayName).build()

                firebaseUser?.updateProfile(profileUpdates)

                verifyEmail()

                response.setSuccess(true)
                RxBus.publish(response)
            } else {
                val exception = task.exception as FirebaseAuthException

                response.setSuccess(false)
                val errors: MutableList<String> = ArrayList()

                when (exception.errorCode) {
                    FirebaseConstant.ERROR_EMAIL_ALREADY_IN_USE -> {
                        errors.add(FirebaseConstant.ERROR_EMAIL_ALREADY_IN_USE)
                    }
                    FirebaseConstant.ERROR_WEAK_PASSWORD -> {
                        errors.add(FirebaseConstant.ERROR_WEAK_PASSWORD)
                    }
                }
                response.setErrors(errors)
                RxBus.publish(response)
            }
        }
    }

    fun signIn(context: Context, email: String, password: String) {
        val response = FirebaseResponse()
        response.setType(FirebaseConstant.TYPE_LOGIN)

        firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener(context as Activity) { task ->
            if (task.isSuccessful) {
                firebaseUser = firebaseAuth?.currentUser
                authenticated = true

                if(firebaseUser?.isEmailVerified == true){
                    verified = true
                }

                firebaseUser?.uid?.let {
                    userManager.fetchUser(it)
                }
            } else {
                val exception = task.exception as FirebaseAuthException
                response.setSuccess(false)
                val errors: MutableList<String> = ArrayList()

                when (exception.errorCode) {
                    FirebaseConstant.ERROR_USER_NOT_FOUND -> {
                        errors.add(FirebaseConstant.ERROR_USER_NOT_FOUND)
                    }
                    FirebaseConstant.ERROR_WRONG_PASSWORD -> {
                        errors.add(FirebaseConstant.ERROR_WRONG_PASSWORD)
                    }
                }
                response.setErrors(errors)
                RxBus.publish(response)
            }
        }
    }

    fun resetPassword(context: Context, email: String) {
        val response = FirebaseResponse()
        response.setType(FirebaseConstant.TYPE_PASSWORD_RESET)

        firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth?.sendPasswordResetEmail(email)?.addOnCompleteListener(context as Activity) { task ->
            if (task.isSuccessful) {
                response.setSuccess(true)
                RxBus.publish(response)
            } else {
                val exception = task.exception as FirebaseAuthException
                response.setSuccess(false)
                val errors: MutableList<String> = ArrayList()

                when (exception.errorCode) {
                    FirebaseConstant.ERROR_USER_NOT_FOUND -> {
                        errors.add(FirebaseConstant.ERROR_USER_NOT_FOUND)
                    }
                    FirebaseConstant.ERROR_INVALID_EMAIL -> {
                        errors.add(FirebaseConstant.ERROR_INVALID_EMAIL)
                    }
                }
                response.setErrors(errors)
                RxBus.publish(response)
            }
        }
    }

    fun verifyEmail() {
        if(isLoggedIn() && !isVerified()) {
            firebaseUser?.sendEmailVerification()
        }
    }

    fun refreshUser(context: Context) {
        if(isLoggedIn()) {
            firebaseUser?.reload()?.addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    if(firebaseUser?.isEmailVerified == true) {
                        verified = true
                        val response = FirebaseResponse()
                        response.setType(FirebaseConstant.TYPE_EMAIL_VERIFICATION)
                        response.setSuccess(true)
                        RxBus.publish(response)
                    }
                } else {
                    val exception = task.exception as FirebaseAuthException
                    println("reload error: " + exception.message)
                }
            }
        }
    }

    fun signOut() {
        if (firebaseUser != null) {
            firebaseAuth?.signOut()
            firebaseUser = null
            authenticated = false
            fragmentNavigator.clearBackStack()
        }
    }

    fun isLoggedIn() : Boolean {
        return authenticated
    }

    fun isVerified() : Boolean {
        return verified
    }
}