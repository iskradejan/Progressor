package com.progressor.progressor.views.fragment

import android.os.Bundle
import android.view.View
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.model.constant.UserConstant
import com.progressor.progressor.views.presenter.NewBodyPresenter
import kotlinx.android.synthetic.main.layout_new_body.*
import javax.inject.Inject

class NewBodyFragment : BaseFragment(), NewBodyPresenter.View {

    @Inject
    lateinit var presenter: NewBodyPresenter

    private fun initialize() {
        genderHandler()

        newBodyWeightEdit.setText(userManager.user?.person?.weight.toString())
        newBodyWaistEdit.requestFocus()

        newBodyMoodGreat.setOnClickListener {
            moodToggleHandler()
            newBodyMoodGreat.setImageResource(R.drawable.mood_great_active)
        }

        newBodyMoodOkay.setOnClickListener {
            moodToggleHandler()
            newBodyMoodOkay.setImageResource(R.drawable.mood_okay_active)
        }

        newBodyMoodNeutral.setOnClickListener {
            moodToggleHandler()
            newBodyMoodNeutral.setImageResource(R.drawable.mood_neutral_active)
        }

        newBodyMoodBad.setOnClickListener {
            moodToggleHandler()
            newBodyMoodBad.setImageResource(R.drawable.mood_bad_active)
        }

        newBodyMoodTerrible.setOnClickListener {
            moodToggleHandler()
            newBodyMoodTerrible.setImageResource(R.drawable.mood_terrible_active)
        }
    }

    private fun genderHandler() {
        if (userManager.user?.person?.gender == UserConstant.PERSON_GENDER_FEMALE) {
            newBodyWristLabel.visibility = View.VISIBLE
            newBodyWristEdit.visibility = View.VISIBLE

            newBodyHipLabel.visibility = View.VISIBLE
            newBodyHipEdit.visibility = View.VISIBLE

            newBodyForearmLabel.visibility = View.VISIBLE
            newBodyForearmEdit.visibility = View.VISIBLE
        }
    }

    private fun moodToggleHandler() {
        newBodyMoodGreat.setImageResource(R.drawable.mood_great_inactive)
        newBodyMoodOkay.setImageResource(R.drawable.mood_okay_inactive)
        newBodyMoodNeutral.setImageResource(R.drawable.mood_neutral_inactive)
        newBodyMoodBad.setImageResource(R.drawable.mood_bad_inactive)
        newBodyMoodTerrible.setImageResource(R.drawable.mood_terrible_inactive)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDependencies()
        setSidePane()
        initialize()
    }

    override fun injectDependencies() {
        (activity as MainComponentInterface).mainComponent.inject(this)
        presenter.setPresenter(this)
    }

    override fun setSidePane() {
        sidePaneManager.showSidePane(true)
    }

    override fun getFragmentLayout(): Int {
        return R.layout.layout_new_body
    }
}