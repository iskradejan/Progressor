package com.progressor.progressor.presenter

import android.content.Context
import com.progressor.progressor.R
import com.progressor.progressor.model.dataobjects.account.Body
import com.progressor.progressor.service.AuthenticationManager
import com.progressor.progressor.service.FragmentNavigator
import com.progressor.progressor.service.UserManager
import com.progressor.progressor.view.DashboardFragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DashboardPresenter @Inject constructor(
        var fragmentNavigator: FragmentNavigator,
        var authenticationManager: AuthenticationManager,
        var userManager: UserManager) {

    private lateinit var view: View
    private var context: Context? = null

    interface View {}

    fun setPresenter(dashboardFragment: DashboardFragment) {
        view = dashboardFragment
        context = dashboardFragment.context
    }

    fun latestBody(): Body {
        userManager.user?.bodyHistory?.sortedWith(compareBy({ it.createDate }))?.let {
            if(it.isNotEmpty()) {
                return it.first()
            }
        }

        return Body()
    }

    fun isNewBodyEligible(): Boolean {
        userManager.user?.bodyHistory?.sortedWith(compareBy({ it.createDate }))?.let {
            if (it.isNotEmpty()) {
                if (isEligible(it.last().createDate, context?.resources?.getInteger(R.integer.newBodyLimit)?.toLong()?:0)) {
                    return true
                }
            }
        }

        return false
    }

    private fun isEligible(localDateTime: String, months: Long): Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val formatDateTime = LocalDateTime.parse(localDateTime, formatter)
        return formatDateTime.isAfter(LocalDateTime.now().plusMonths(months))
    }
}