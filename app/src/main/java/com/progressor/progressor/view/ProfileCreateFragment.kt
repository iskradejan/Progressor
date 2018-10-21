package com.progressor.progressor.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import kotlinx.android.synthetic.main.layout_profile_create.*
import java.text.SimpleDateFormat
import java.util.*
import android.widget.SeekBar
import android.widget.Toast
import com.progressor.progressor.model.constant.FirebaseConstant
import com.progressor.progressor.model.constant.UserConstant
import com.progressor.progressor.model.dataobjects.helper.FirebaseResponse
import com.progressor.progressor.service.RxBus
import com.progressor.progressor.presenter.ProfileCreatePresenter
import java.time.LocalDate
import javax.inject.Inject

class ProfileCreateFragment : BaseFragment(), ProfileCreatePresenter.View {

    @Inject
    lateinit var presenter: ProfileCreatePresenter

    private var gender : Int = 0
    private var height : Int = 0
    private var weight : Int = 0
    private var dob : String = ""

    fun initialize() {
        RxBus.subscribe<FirebaseResponse>(this) {
            if (it.getType().equals(FirebaseConstant.TYPE_CREATE_PROFILE)) {
                when (it.getSuccess()) {
                    true -> {
                        fragmentNavigator.to(EmptyDashboardFragment())
                    }
                    false -> {
                        context.let { context ->
                            Toast.makeText(context, "Something went wrong. Try again", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

        context?.let {context ->
            // Date picker listener
            val calendar = Calendar.getInstance()

            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val format = "MM/dd/YYYY"
                val simpleDateFormat = SimpleDateFormat(format, Locale.US)
                profileCreateBirthdayValue.text = simpleDateFormat.format(calendar.time)

                dob = LocalDate.of(year, monthOfYear, dayOfMonth).atStartOfDay().toString()
            }

            // Birthday
            profileCreateBirthdayValue.setOnClickListener {
                DatePickerDialog(context, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
            }

            // Gender Female
            profileCreateFemaleButton.setOnClickListener {
                profileCreateFemaleButton.setImageResource(R.drawable.female_active)
                profileCreateMaleButton.setImageResource(R.drawable.male_inactive)
                gender = UserConstant.PERSON_GENDER_FEMALE
            }

            // Gender Male
            profileCreateMaleButton.setOnClickListener {
                profileCreateFemaleButton.setImageResource(R.drawable.female_inactive)
                profileCreateMaleButton.setImageResource(R.drawable.male_active)
                gender = UserConstant.PERSON_GENDER_MALE
            }

            // Height
            profileCreateHeightSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    val inches = progress
                    val feet = (inches / 12) + 3
                    val leftover = inches % 12
                    val value = "" + feet + " ft " + leftover + " in"

                    height = (feet * 12) + leftover

                    profileCreateHeightValue.text = value
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}

            })

            // Weight
            profileCreateWeightSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    val value = "" + (progress + 50) + " lb"

                    weight = progress + 50

                    profileCreateWeightValue.text = value
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}

            })

            // Continue
            profileCreateContinueButton.setOnClickListener {
                presenter.createProfile(gender, height, weight, dob)
            }
        }
    }

    override fun isFormValid(): Boolean {
        var valid = true

        if(gender == 0) {
            profileCreateGenderLabel.setError("Required")
            valid = false
        } else {
            profileCreateGenderLabel.setError(null)
        }

        if(height == 0) {
            profileCreateHeightLabel.setError("Required")
            valid = false
        } else {
            profileCreateHeightLabel.setError(null)
        }

        if(weight == 0) {
            profileCreateWeightLabel.setError("Required")
            valid = false
        } else {
            profileCreateWeightLabel.setError(null)
        }

        if("".equals(dob)) {
            profileCreateBirthdayLabel.setError("Required")
            valid = false
        } else {
            profileCreateBirthdayLabel.setError(null)
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
        sidePaneManager.showSidePane(false)
    }

    override fun getFragmentLayout(): Int {
        return R.layout.layout_profile_create
    }
}