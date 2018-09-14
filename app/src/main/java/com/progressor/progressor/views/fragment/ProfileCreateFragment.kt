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
            val calendar = Calendar.getInstance()

            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val format = "MM/dd/YYYY" // mention the format you need
                val simpleDateFormat = SimpleDateFormat(format, Locale.US)
                profileCreateBirthdayValue.text = simpleDateFormat.format(calendar.time)
            }

            profileCreateBirthdayValue.setOnClickListener {
                DatePickerDialog(context, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
            }

            profileCreateContinue.setOnClickListener {
                val toast = Toast.makeText(context, "YAYYY", Toast.LENGTH_LONG)
                toast.show()
            }
        }

    }
}