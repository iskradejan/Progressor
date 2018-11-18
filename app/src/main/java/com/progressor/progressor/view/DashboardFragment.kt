package com.progressor.progressor.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.progressor.progressor.presenter.DashboardPresenter
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import kotlinx.android.synthetic.main.layout_dashboard.*
import javax.inject.Inject
import android.support.v4.content.ContextCompat
import android.widget.Toast

class DashboardFragment : BaseFragment(), DashboardPresenter.View {
    @Inject
    lateinit var presenter: DashboardPresenter
    lateinit var currentView: View
    private var currentHex = ""

    private fun initialize() {
        val body = presenter.latestBody()
        dashboardBodyPieChart.setValues(body.musclePercentage ?: 0, body.fatPercentage
                ?: 0, resources.getInteger(R.integer.bonePercent))

        body.musclePercentage?.let {
            dashboardBodyPieChartText.text = String.format("%d%%", it)
        }

        dashboardBodyPieChartText.setOnClickListener {
            fragmentNavigator.to(BodyHistoryFragment())
        }

        dashboardBodyPieChart.setOnTouchListener { v, event ->
            try {
                val bitmap = loadBitmapFromView(v)
                val canvas = Canvas(bitmap)
                v.draw(canvas)
                val pixel = bitmap.getPixel(event.x.toInt(), event.y.toInt())
                val hexColor = rgbToHex(Color.red(pixel), Color.green(pixel), Color.blue(pixel))

                if (!currentHex.equals(hexColor)) {
                    currentHex = hexColor
                    when (hexColor) {
                        colorToHex(R.color.baseMuscle) -> {
                            body.musclePercentage?.let {
                                dashboardBodyPieChartText.text = String.format("%d%%", it)
                            }
                        }
                        colorToHex(R.color.baseFat) -> {
                            body.fatPercentage?.let {
                                dashboardBodyPieChartText.text = String.format("%d%%", it)
                            }
                        }
                        colorToHex(R.color.baseBone) -> {
                            dashboardBodyPieChartText.text = String.format("%d%%", resources.getInteger(R.integer.bonePercent))
                        }
                    }
                }
            } catch (exception: Exception) {
                // TODO: Handle exception
            }
            true
        }

        dashboardAdd.setOnTouchListener { v, event ->
            if (event.y <= -200) {
                fragmentNavigator.to(NewWorkoutFragment())
            }

            if (event.y >= 200) {
                if (presenter.isNewBodyEligible()) {
                    fragmentNavigator.to(NewBodyFragment())
                } else {
                    Toast.makeText(context, "Give it some rest, record once a month", Toast.LENGTH_LONG).show()
                }
            }
            true
        }
    }


    private fun rgbToHex(r: Int, g: Int, b: Int): String {
        return String.format("#%02x%02x%02x", r, g, b)
    }

    private fun colorToHex(color: Int): String {
        return "#" + Integer.toHexString(ContextCompat.getColor(activity!!, color) and 0x00ffffff)
    }

    private fun loadBitmapFromView(view: View): Bitmap {
        return Bitmap.createBitmap(view.layoutParams.width, view.layoutParams.height, Bitmap.Config.ARGB_8888)
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
        return R.layout.layout_dashboard
    }
}