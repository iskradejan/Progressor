package com.progressor.progressor.views.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import kotlinx.android.synthetic.main.layout_profile_create.*
import java.text.SimpleDateFormat
import java.util.*
import android.widget.Toast
import android.widget.SeekBar

class ProfileCreateFragment : BaseFragment() {

    override fun getFragmentLayout(): Int {
        return R.layout.layout_profile_create
    }

    override fun injectDependencies() {
        (activity as MainComponentInterface).mainComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDependencies()
        initialize()
    }

    fun initialize() {
        context?.let {context ->
            // Date picker
            val calendar = Calendar.getInstance()

            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val format = "  MM/dd/YYYY  " // mention the format you need
                val simpleDateFormat = SimpleDateFormat(format, Locale.US)
                profileCreateBirthdayValue.text = simpleDateFormat.format(calendar.time)
            }

            // Birthday
            profileCreateBirthdayValue.setOnClickListener {
                DatePickerDialog(context, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
            }

            // Gender Female
            profileCreateFemaleButton.setOnClickListener {
                profileCreateFemaleButton.setImageResource(R.drawable.female_active)
                profileCreateMaleButton.setImageResource(R.drawable.male_inactive)
            }

            // Gender Male
            profileCreateMaleButton.setOnClickListener {
                profileCreateFemaleButton.setImageResource(R.drawable.female_inactive)
                profileCreateMaleButton.setImageResource(R.drawable.male_active)
            }

            // Height
            profileCreateHeightSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    val inches = progress
                    val feet = (inches / 12) + 3
                    val leftover = inches % 12
                    val value = "" + feet + " ft " + leftover + " in"

                    profileCreateHeightValue.text = value
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}

            })

            // Weight
            profileCreateWeightSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    val value = "" + (progress + 50) + " lb"

                    profileCreateWeightValue.text = value
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}

            })

            // Continue
            profileCreateContinue.setOnClickListener {
                val toast = Toast.makeText(context, "Only Visuals Work", Toast.LENGTH_LONG)
                toast.show()
            }
        }

    }
}