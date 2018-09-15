package com.progressor.progressor.views.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import kotlinx.android.synthetic.main.layout_profile_create.*
import java.text.SimpleDateFormat
import java.util.*
import android.widget.SeekBar
import com.progressor.progressor.views.presenter.ProfileCreatePresenter
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class ProfileCreateFragment : BaseFragment(), ProfileCreatePresenter.View {
    @Inject
    lateinit var presenter: ProfileCreatePresenter

    private var gender : Int ?= null
    private var height : Int ?= null
    private var weight : Int ?= null
    private var dob : LocalDateTime ?= null
    private var createDate = LocalDateTime.now()

    fun initialize() {
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

                dob = LocalDate.of(year, monthOfYear, dayOfMonth).atStartOfDay()
            }

            // Birthday
            profileCreateBirthdayValue.setOnClickListener {
                DatePickerDialog(context, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
            }

            // Gender Female
            profileCreateFemaleButton.setOnClickListener {
                profileCreateFemaleButton.setImageResource(R.drawable.female_active)
                profileCreateMaleButton.setImageResource(R.drawable.male_inactive)
                gender = 0
            }

            // Gender Male
            profileCreateMaleButton.setOnClickListener {
                profileCreateFemaleButton.setImageResource(R.drawable.female_inactive)
                profileCreateMaleButton.setImageResource(R.drawable.male_active)
                gender = 1
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
                presenter.createProfile(gender, height, weight, dob, createDate)
            }
        }
    }

    override fun isFormValid(): Boolean {
        var valid = true

        if(gender == null) {
            profileCreateGenderLabel.setError("Required")
            valid = false
        } else {
            profileCreateGenderLabel.setError(null)
        }

        if(height == null) {
            profileCreateHeightLabel.setError("Required")
            valid = false
        } else {
            profileCreateHeightLabel.setError(null)
        }

        if(weight == null) {
            profileCreateWeightLabel.setError("Required")
            valid = false
        } else {
            profileCreateWeightLabel.setError(null)
        }

        if(dob == null) {
            profileCreateBirthdayLabel.setError("Required")
            valid = false
        } else {
            profileCreateBirthdayLabel.setError(null)
        }

        return valid
    }

    override fun getFragmentLayout(): Int {
        return R.layout.layout_profile_create
    }

    override fun injectDependencies() {
        (activity as MainComponentInterface).mainComponent.inject(this)
        presenter.setPresenter(this)
        initialize()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDependencies()
        initialize()
    }
}