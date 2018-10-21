package com.progressor.progressor.presenter

import com.progressor.progressor.model.constant.UserConstant
import com.progressor.progressor.model.dataobjects.account.Body
import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.service.UserManager
import com.progressor.progressor.view.BodyHistoryFragment
import com.progressor.progressor.view.LoginFragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class BodyHistoryPresenter @Inject constructor(private var fragmentNavigator: FragmentNavigator, private var userManager: UserManager, private var authenticationManager: AuthenticationManager) {
    private lateinit var view: View
    private lateinit var lastBody: Body
    private val user = userManager.user
    private val monthlyDecremental = 1L
    private val fatPercentageDecremental = 1
    private val musclePercentageIncremental = 1
    private var bodyList: MutableList<Body>? = null

    interface View {
        fun totalBars(): Int
    }

    fun setPresenter(bodyHistoryFragment: BodyHistoryFragment) {
        view = bodyHistoryFragment
        initialize()
    }

    private fun initialize() {
        if (!authenticationManager.isLoggedIn()) {
            fragmentNavigator.to(LoginFragment())
        }

        bodyList = user?.bodyHistory?.sortedWith(compareBy({it.createDate}))?.toMutableList()
    }

    fun getRealBodySize(): Int {
        return bodyList?.size ?: 0
    }

    fun getFullBodyList(): MutableList<Body>? {
        val newBodyList = bodyList

        newBodyList?.let { list ->
            lastBody = list.last()
            if (list.size < view.totalBars()) {
                val difference = view.totalBars().minus(list.size)
                for (i in 1..difference) {
                    list.add(Body(
                            mood = UserConstant.PERSON_MOOD_GREAT,
                            fatPercentage = lastBody.fatPercentage?.minus(fatPercentageDecremental),
                            musclePercentage = lastBody.musclePercentage?.plus(musclePercentageIncremental),
                            createDate = modifyDate(list.last().createDate, monthlyDecremental)))
                }
            }
        }

        return newBodyList
    }

    private fun predict() {

    }

    private fun modifyDate(localDateTime: String, months: Long): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val formatDateTime = LocalDateTime.parse(localDateTime, formatter)
        return formatDateTime.plusMonths(months).format(formatter)
    }
}