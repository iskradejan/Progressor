package com.progressor.progressor.view

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import android.widget.TextView
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.model.constant.UserConstant
import com.progressor.progressor.presenter.BodyHistoryPresenter
import kotlinx.android.synthetic.main.layout_body_history.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

class BodyHistoryFragment : BaseFragment(), BodyHistoryPresenter.View {

    @Inject
    lateinit var presenter: BodyHistoryPresenter
    private var dateLabels = ArrayList<TextView>()

    private fun initialize() {
        setDateLabelArray()
        setBars()
    }

    private fun setDateLabelArray() {
        dateLabels = arrayListOf<TextView>(bodyHistoryAxisYLabel1, bodyHistoryAxisYLabel2, bodyHistoryAxisYLabel3, bodyHistoryAxisYLabel4, bodyHistoryAxisYLabel5, bodyHistoryAxisYLabel6, bodyHistoryAxisYLabel7, bodyHistoryAxisYLabel8)
    }

    private fun setBars() {
        presenter.getBodyList()?.forEachIndexed { index, body ->
            println("index: " + index)
            dateLabels[index].text = reformatDate(body.createDate)
            getBarContainers().forEach { baseKey, baseValue ->
                baseValue.forEach { barKey, barMood ->
                    barKey.forEach { muscleContainer, fatContainer ->
                        val barWidth = muscleContainer.measuredWidth.plus(fatContainer.measuredWidth)
                        body.musclePercentage ?.let {
                            val muscleLayoutParameters = muscleContainer.getLayoutParams() as ConstraintLayout.LayoutParams
                            muscleLayoutParameters.width = (barWidth.times((it.div(100)))).toInt()
                            muscleContainer.setLayoutParams(muscleLayoutParameters)
                        }
                        body.fatPercentage?.let {
                            val fatLayoutParameters = fatContainer.getLayoutParams() as ConstraintLayout.LayoutParams
                            fatLayoutParameters.width = (barWidth.times((it.div(100)))).toInt()
                            fatContainer.setLayoutParams(fatLayoutParameters)
                        }
                    }

                    barMood.background = context?.getDrawable(getMoodBackground(body.mood))
                }
            }
        }
    }

    private fun getMoodBackground(mood: Int): Int {
        when (mood) {
            UserConstant.PERSON_MOOD_GREAT -> { return R.drawable.mood_great_active }
            UserConstant.PERSON_MOOD_OKAY -> { return R.drawable.mood_okay_active }
            UserConstant.PERSON_MOOD_NEUTRAL -> { return R.drawable.mood_okay_active }
            UserConstant.PERSON_MOOD_BAD -> { return R.drawable.mood_bad_active }
            UserConstant.PERSON_MOOD_TERRIBLE -> { return R.drawable.mood_terrible_active }
        }

        return 0
    }

    private fun getBarContainers(): LinkedHashMap<ConstraintLayout, LinkedHashMap<LinkedHashMap<ConstraintLayout, ConstraintLayout>, ConstraintLayout>> {
        // 1
        val barMap1 = LinkedHashMap<ConstraintLayout, ConstraintLayout>()
        barMap1.put(bodyHistoryBarMuscle1, bodyHistoryBarFat1)
        val bodyContainerMap1 = LinkedHashMap<LinkedHashMap<ConstraintLayout, ConstraintLayout>, ConstraintLayout>()
        bodyContainerMap1.put(barMap1, bodyHistoryBarMood1)
        // 2
        val barMap2 = LinkedHashMap<ConstraintLayout, ConstraintLayout>()
        barMap2.put(bodyHistoryBarMuscle2, bodyHistoryBarFat2)
        val bodyContainerMap2 = LinkedHashMap<LinkedHashMap<ConstraintLayout, ConstraintLayout>, ConstraintLayout>()
        bodyContainerMap2.put(barMap2, bodyHistoryBarMood2)
        // 3
        val barMap3 = LinkedHashMap<ConstraintLayout, ConstraintLayout>()
        barMap3.put(bodyHistoryBarMuscle3, bodyHistoryBarFat3)
        val bodyContainerMap3 = LinkedHashMap<LinkedHashMap<ConstraintLayout, ConstraintLayout>, ConstraintLayout>()
        bodyContainerMap3.put(barMap3, bodyHistoryBarMood3)
        // 4
        val barMap4 = LinkedHashMap<ConstraintLayout, ConstraintLayout>()
        barMap4.put(bodyHistoryBarMuscle4, bodyHistoryBarFat4)
        val bodyContainerMap4 = LinkedHashMap<LinkedHashMap<ConstraintLayout, ConstraintLayout>, ConstraintLayout>()
        bodyContainerMap4.put(barMap4, bodyHistoryBarMood4)
        // 5
        val barMap5 = LinkedHashMap<ConstraintLayout, ConstraintLayout>()
        barMap5.put(bodyHistoryBarMuscle5, bodyHistoryBarFat5)
        val bodyContainerMap5 = LinkedHashMap<LinkedHashMap<ConstraintLayout, ConstraintLayout>, ConstraintLayout>()
        bodyContainerMap5.put(barMap5, bodyHistoryBarMood5)
        // 6
        val barMap6 = LinkedHashMap<ConstraintLayout, ConstraintLayout>()
        barMap6.put(bodyHistoryBarMuscle6, bodyHistoryBarFat6)
        val bodyContainerMap6 = LinkedHashMap<LinkedHashMap<ConstraintLayout, ConstraintLayout>, ConstraintLayout>()
        bodyContainerMap6.put(barMap6, bodyHistoryBarMood6)
        // 7
        val barMap7 = LinkedHashMap<ConstraintLayout, ConstraintLayout>()
        barMap7.put(bodyHistoryBarMuscle7, bodyHistoryBarFat7)
        val bodyContainerMap7 = LinkedHashMap<LinkedHashMap<ConstraintLayout, ConstraintLayout>, ConstraintLayout>()
        bodyContainerMap7.put(barMap7, bodyHistoryBarMood7)
        // 8
        val barMap8 = LinkedHashMap<ConstraintLayout, ConstraintLayout>()
        barMap8.put(bodyHistoryBarMuscle8, bodyHistoryBarFat8)
        val bodyContainerMap8 = LinkedHashMap<LinkedHashMap<ConstraintLayout, ConstraintLayout>, ConstraintLayout>()
        bodyContainerMap8.put(barMap8, bodyHistoryBarMood8)

        val barContainerMap = LinkedHashMap<ConstraintLayout, LinkedHashMap<LinkedHashMap<ConstraintLayout, ConstraintLayout>, ConstraintLayout>>()
        barContainerMap.put(bodyHistoryBodyContainer1, bodyContainerMap1)
        barContainerMap.put(bodyHistoryBodyContainer2, bodyContainerMap2)
        barContainerMap.put(bodyHistoryBodyContainer3, bodyContainerMap3)
        barContainerMap.put(bodyHistoryBodyContainer4, bodyContainerMap4)
        barContainerMap.put(bodyHistoryBodyContainer5, bodyContainerMap5)
        barContainerMap.put(bodyHistoryBodyContainer6, bodyContainerMap6)
        barContainerMap.put(bodyHistoryBodyContainer7, bodyContainerMap7)
        barContainerMap.put(bodyHistoryBodyContainer8, bodyContainerMap8)

        return barContainerMap
    }

    private fun reformatDate(localDateTime: String): String {
        val fromFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)
        val toFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)
        val date = fromFormat.parse(localDateTime)
        return toFormat.format(date)
    }

    override fun totalBars(): Int {
        return dateLabels.size
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
        return R.layout.layout_body_history
    }
}