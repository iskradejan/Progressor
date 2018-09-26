package com.progressor.progressor.views.fragment

import android.os.Bundle
import android.text.TextUtils
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

    private var isMoodSelected = false

    private fun initialize() {
        genderFieldHandler()

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

        newBodySubmit.setOnClickListener {
            presenter.addBody()
        }
    }

    private fun isFemale(): Boolean {
        return userManager.user?.person?.gender == UserConstant.PERSON_GENDER_FEMALE
    }

    private fun genderFieldHandler() {
        if (isFemale()) {
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

        newBodyMoodLabel.setError(null)
        isMoodSelected = true
    }

    override fun isFormValid(): Boolean {
        var valid = true

        if (TextUtils.isEmpty(newBodyWaistEdit.getText().toString())) {
            newBodyWaistEdit.setError("Required")
            valid = false
        } else {
            newBodyWaistEdit.setError(null)
        }

        if (isFemale()) {
            if (TextUtils.isEmpty(newBodyWristEdit.getText().toString())) {
                newBodyWristEdit.setError("Required")
                valid = false
            } else {
                newBodyWristEdit.setError(null)
            }

            if (TextUtils.isEmpty(newBodyHipEdit.getText().toString())) {
                newBodyHipEdit.setError("Required")
                valid = false
            } else {
                newBodyHipEdit.setError(null)
            }

            if (TextUtils.isEmpty(newBodyForearmEdit.getText().toString())) {
                newBodyForearmEdit.setError("Required")
                valid = false
            } else {
                newBodyForearmEdit.setError(null)
            }
        }

        if(!isMoodSelected) {
            newBodyMoodLabel.setError("Required")
            valid = false
        } else {
            newBodyMoodLabel.setError(null)
        }

        return valid
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