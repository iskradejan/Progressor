package com.progressor.progressor.view

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.progressor.progressor.MainActivity
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.model.constant.FirebaseConstant
import com.progressor.progressor.model.dataobjects.helper.FirebaseResponse
import com.progressor.progressor.presenter.NewDejanDayTwoPresenter
import com.progressor.progressor.service.RxBus
import com.progressor.progressor.service.dateTimeStampToDisplay
import kotlinx.android.synthetic.main.layout_day_two_history.*
import kotlinx.android.synthetic.main.layout_new_dejan_day_two.*
import javax.inject.Inject

class NewDejanDayTwoFragment : BaseFragment(), NewDejanDayTwoPresenter.View {
    @Inject
    lateinit var presenter: NewDejanDayTwoPresenter
    lateinit var defaultLabelColor: ColorStateList
    lateinit var currentView: View

    private fun initialize() {
        (activity as MainActivity).setBackFragment(DashboardFragment())

        RxBus.subscribe<FirebaseResponse>(this) {
            if (it.getType().equals(FirebaseConstant.TYPE_DEJAN_DAY_TWO)) {
                when (it.getSuccess()) {
                    true -> {
                        context.let { context ->
                            Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
                            resetForm()
                        }
                    }
                    false -> {
                        context.let { context ->
                            Toast.makeText(context, "Trouble saving. Try again", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        if(presenter.hasHistory()) {
            newDejanTwoHistory.visibility = View.VISIBLE
            newDejanTwoHistory.setOnClickListener {
                context?.let {
                    val inflater = layoutInflater
                    val alertLayout = inflater.inflate(R.layout.layout_day_two_history, null)
                    val lastEntry = userManager.user?.workout?.dejan?.dayTwoList?.last()
                    val alert = AlertDialog.Builder(context)
                    alert.setView(alertLayout)
                    val dialog = alert.create()

                    dialog.show()

                    dialog.dejanTwoHistoryEllipticalAmount.text = lastEntry?.elliptical

                    lastEntry?.let {
                        dialog.dejanTwoHistoryDate.text = dateTimeStampToDisplay(it.createDate)
                    }
                }
            }
        }

        defaultLabelColor = newDejanDayTwoEllipticalLabel.textColors

        newDejanWorkoutDayOne.setOnClickListener {
            fragmentNavigator.to(NewDejanDayOneFragment())
        }
        newDejanWorkoutDayThree.setOnClickListener {
            fragmentNavigator.to(NewDejanDayThreeFragment())
        }
        newDejanWorkoutDayFour.setOnClickListener {
            fragmentNavigator.to(NewDejanDayFourFragment())
        }
        newDejanWorkoutDayFive.setOnClickListener {
            fragmentNavigator.to(NewDejanDayFiveFragment())
        }
        newDejanDayTwoSaveButton.setOnClickListener {
            presenter.save()
        }
    }

    private fun resetForm() {
        newDejanDayTwoEllipticalAmount.setText("")
    }

    override fun getElliptical(): String {
        return newDejanDayTwoEllipticalAmount.text.toString()
    }

    override fun isFormValid(): Boolean {

        if (newDejanDayTwoEllipticalAmount.text.toString().isEmpty() || newDejanDayTwoEllipticalAmount.text.toString().length > 4) {
            newDejanDayTwoEllipticalAmount.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.baseMaroon, null))
            return false
        } else {
            newDejanDayTwoEllipticalAmount.backgroundTintList = defaultLabelColor
            return true
        }
    }

    override fun onStop() {
        super.onStop()
        RxBus.unsubscribe(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentView = view
        injectDependencies()
        setSidePane()
        initialize()
    }

    override fun setSidePane() {
        sidePaneManager.showSidePane(true)
    }

    override fun injectDependencies() {
        (activity as MainComponentInterface).mainComponent.inject(this)
        presenter.setPresenter(this)
    }

    override fun getFragmentLayout(): Int {
        return R.layout.layout_new_dejan_day_two
    }
}