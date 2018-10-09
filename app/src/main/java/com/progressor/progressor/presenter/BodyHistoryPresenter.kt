package com.progressor.progressor.presenter

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
    private val user = userManager.user
    private val monthlyDecremental = 1L

    interface View {
        fun totalDateLabels(): Int
    }

    fun setPresenter(bodyHistoryFragment: BodyHistoryFragment) {
        view = bodyHistoryFragment
        initialize()
    }

    private fun initialize() {
        if (!authenticationManager.isLoggedIn()) {
            fragmentNavigator.navigate(LoginFragment())
        }
    }

    fun getBodyList(): MutableList<Body>? {
        val newBodyList: MutableList<Body>? = user?.bodyHistory?.sortedWith(compareBy({it.createDate}))?.reversed()?.toMutableList()

        newBodyList?.let { list ->
            if (list.size < view.totalDateLabels()) {
                val difference = view.totalDateLabels().minus(list.size)
                for (i in 1..difference) {
                    list.add(Body(createDate = convertDate(list.last().createDate, monthlyDecremental)))
                }
            }
        }

        return newBodyList
    }

    private fun convertDate(localDateTime: String, months: Long): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val formatDateTime = LocalDateTime.parse(localDateTime, formatter)
        return formatDateTime.minusMonths(months).format(formatter)
    }
}