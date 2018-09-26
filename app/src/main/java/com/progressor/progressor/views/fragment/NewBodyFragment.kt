package com.progressor.progressor.views.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.model.constant.FirebaseConstant
import com.progressor.progressor.model.constant.UserConstant
import com.progressor.progressor.model.dataobjects.helper.FirebaseResponse
import com.progressor.progressor.services.RxBus
import com.progressor.progressor.views.presenter.NewBodyPresenter
import kotlinx.android.synthetic.main.layout_new_body.*
import javax.inject.Inject

class NewBodyFragment : BaseFragment(), NewBodyPresenter.View {

    @Inject
    lateinit var presenter: NewBodyPresenter

    private var isMoodSelected = false
    private var weight: Double = 0.0
    private var waist: Double = 0.0
    private var wrist: Double? = null
    private var hip: Double? = null
    private var forearm: Double? = null
    private var mood: Int = 0

    private fun initialize() {
        RxBus.subscribe<FirebaseResponse>(this) {
            if (it.getType().equals(FirebaseConstant.TYPE_NEW_BODY)) {
                when (it.getSuccess()) {
                    true -> {
                        // TODO: Go somewhere - real dashboard?
//                        fragmentNavigator.navigate(EmptyDashboardFragment())
                    }
                    false -> {
                        context.let { context ->
                            Toast.makeText(context, "Something went wrong. Try again", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        genderFieldHandler()

        newBodyWeightEdit.setText(userManager.user?.person?.weight.toString())

        newBodyWaistEdit.requestFocus()

        newBodyMoodGreat.setOnClickListener {
            moodToggleHandler(UserConstant.PERSON_MOOD_GREAT)
            newBodyMoodGreat.setImageResource(R.drawable.mood_great_active)
        }

        newBodyMoodOkay.setOnClickListener {
            moodToggleHandler(UserConstant.PERSON_MOOD_OKAY)
            newBodyMoodOkay.setImageResource(R.drawable.mood_okay_active)
        }

        newBodyMoodNeutral.setOnClickListener {
            moodToggleHandler(UserConstant.PERSON_MOOD_NEUTRAL)
            newBodyMoodNeutral.setImageResource(R.drawable.mood_neutral_active)
        }

        newBodyMoodBad.setOnClickListener {
            moodToggleHandler(UserConstant.PERSON_MOOD_BAD)
            newBodyMoodBad.setImageResource(R.drawable.mood_bad_active)
        }

        newBodyMoodTerrible.setOnClickListener {
            moodToggleHandler(UserConstant.PERSON_MOOD_TERRIBLE)
            newBodyMoodTerrible.setImageResource(R.drawable.mood_terrible_active)
        }

        newBodySubmit.setOnClickListener {
            setValues()
            presenter.addBody(weight = weight, waist = waist, mood = mood, wrist = wrist, hip = hip, forearm = forearm)
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

    private fun moodToggleHandler(selectedMood: Int) {
        newBodyMoodGreat.setImageResource(R.drawable.mood_great_inactive)
        newBodyMoodOkay.setImageResource(R.drawable.mood_okay_inactive)
        newBodyMoodNeutral.setImageResource(R.drawable.mood_neutral_inactive)
        newBodyMoodBad.setImageResource(R.drawable.mood_bad_inactive)
        newBodyMoodTerrible.setImageResource(R.drawable.mood_terrible_inactive)

        newBodyMoodLabel.setError(null)
        isMoodSelected = true
        mood = selectedMood
    }

    private fun setValues() {
        if(isFormValid()) {
            weight = newBodyWeightEdit.getText().toString().toDouble()
            waist = newBodyWaistEdit.getText().toString().toDouble()
            if(isFemale()) {
                wrist = newBodyWristEdit.getText().toString().toDouble()
                hip = newBodyHipEdit.getText().toString().toDouble()
                forearm = newBodyForearmEdit.getText().toString().toDouble()
            }
        }
    }

    override fun isFormValid(): Boolean {
        var valid = true

        if (TextUtils.isEmpty(newBodyWeightEdit.getText().toString()) || "0".equals(newBodyWeightEdit.getText().toString()) || "0.0".equals(newBodyWeightEdit.getText().toString())) {
            newBodyWeightEdit.setError("Required")
            valid = false
        } else {
            newBodyWeightEdit.setError(null)
        }

        if (TextUtils.isEmpty(newBodyWaistEdit.getText().toString()) || "0".equals(newBodyWaistEdit.getText().toString()) || "0.0".equals(newBodyWaistEdit.getText().toString())) {
            newBodyWaistEdit.setError("Required")
            valid = false
        } else {
            newBodyWaistEdit.setError(null)
        }

        if (isFemale()) {
            if (TextUtils.isEmpty(newBodyWristEdit.getText().toString()) || "0".equals(newBodyWristEdit.getText().toString()) || "0.0".equals(newBodyWristEdit.getText().toString())) {
                newBodyWristEdit.setError("Required")
                valid = false
            } else {
                newBodyWristEdit.setError(null)
            }

            if (TextUtils.isEmpty(newBodyHipEdit.getText().toString()) || "0".equals(newBodyHipEdit.getText().toString()) || "0.0".equals(newBodyHipEdit.getText().toString())) {
                newBodyHipEdit.setError("Required")
                valid = false
            } else {
                newBodyHipEdit.setError(null)
            }

            if (TextUtils.isEmpty(newBodyForearmEdit.getText().toString()) || "0".equals(newBodyForearmEdit.getText().toString()) || "0.0".equals(newBodyForearmEdit.getText().toString())) {
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