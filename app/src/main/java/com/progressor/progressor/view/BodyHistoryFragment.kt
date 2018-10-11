package com.progressor.progressor.view

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import android.view.ViewTreeObserver
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
    lateinit var currentView: View
    private var dateLabels = ArrayList<TextView>()

    private fun initialize() {
        setDateLabelArray()
        setBars()
    }

    private fun setDateLabelArray() {
        dateLabels = arrayListOf<TextView>(bodyHistoryAxisYLabel1, bodyHistoryAxisYLabel2, bodyHistoryAxisYLabel3, bodyHistoryAxisYLabel4, bodyHistoryAxisYLabel5, bodyHistoryAxisYLabel6, bodyHistoryAxisYLabel7, bodyHistoryAxisYLabel8)
    }

    private fun setBars() {
        var index = 0
        var active = true
        val realBodySize = presenter.getRealBodySize()

        view?.let {
            it.afterMeasured {
                context?.let { context ->
                    presenter.getFullBodyList()?.let { list ->
                        getBarContainers().forEach { baseKey, baseValue ->
                            if(index > realBodySize) {
                                active = false
                            }

                            dateLabels[index].text = reformatDate(list.get(index).createDate)

                            baseValue.forEach { barKey, barMood ->
                                var barWidth = 0
                                barKey.forEach { muscleMap, fatMap ->
                                    muscleMap.forEach { muscleContainer, muscleLabel ->
                                        if(active) {
                                            muscleContainer.background = context.getDrawable(R.color.baseCyan)
                                        }
                                        barWidth = muscleContainer.measuredWidth
                                        list.get(index).musclePercentage?.let { muscle ->
                                            muscleLabel.text = String.format(getString(R.string.body_history_percent), muscle)
                                        }
                                    }

                                    fatMap.forEach { fatContainer, fatLabel ->
                                        if(active) {
                                            fatContainer.background = context.getDrawable(R.color.baseYellow)
                                        }
                                        barWidth += fatContainer.measuredWidth
                                        list.get(index).fatPercentage?.let { fat ->
                                            fatLabel.text = String.format(getString(R.string.body_history_percent), fat)

                                            val fatLayoutParameters = fatContainer.layoutParams
                                            fatLayoutParameters.width = (barWidth.times((fat.div(100))))
                                            fatContainer.layoutParams = fatLayoutParameters
                                        }
                                    }
                                }

                                barMood.background = context.getDrawable(getMoodBackground(list.get(index).mood, active))
                            }
                            index++
                        }
                    }
                }
            }
        }
    }

    inline fun <T: View> T.afterMeasured(crossinline viewFunction: T.() -> Unit) {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (measuredWidth > 0 && measuredHeight > 0) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    viewFunction()
                }
            }
        })
    }

    private fun getMoodBackground(mood: Int, active: Boolean): Int {
        when (mood) {
            UserConstant.PERSON_MOOD_GREAT -> { return if(active) R.drawable.mood_great_active else R.drawable.mood_great_inactive }
            UserConstant.PERSON_MOOD_OKAY -> { return if(active) R.drawable.mood_okay_active else R.drawable.mood_okay_inactive }
            UserConstant.PERSON_MOOD_NEUTRAL -> { return if(active) R.drawable.mood_neutral_active else R.drawable.mood_neutral_inactive }
            UserConstant.PERSON_MOOD_BAD -> { return if(active) R.drawable.mood_bad_active else R.drawable.mood_bad_inactive }
            UserConstant.PERSON_MOOD_TERRIBLE -> { return if(active) R.drawable.mood_terrible_active else R.drawable.mood_terrible_inactive }
        }

        return 0
    }

    private fun getBarContainers(): LinkedHashMap<ConstraintLayout, LinkedHashMap<LinkedHashMap<LinkedHashMap<ConstraintLayout, TextView>, LinkedHashMap<ConstraintLayout, TextView>>, ConstraintLayout>> {
        // 1
        val muscleMap1 = LinkedHashMap<ConstraintLayout, TextView>()
        muscleMap1.put(bodyHistoryBarMuscle1, muscleLabel1)
        val fatMap1 = LinkedHashMap<ConstraintLayout, TextView>()
        fatMap1.put(bodyHistoryBarFat1, fatLabel1)
        val barMap1 = LinkedHashMap<LinkedHashMap<ConstraintLayout, TextView>, LinkedHashMap<ConstraintLayout, TextView>>()
        barMap1.put(muscleMap1, fatMap1)
        val bodyContainerMap1 = LinkedHashMap<LinkedHashMap<LinkedHashMap<ConstraintLayout, TextView>, LinkedHashMap<ConstraintLayout, TextView>>, ConstraintLayout>()
        bodyContainerMap1.put(barMap1, bodyHistoryBarMood1)
        // 2
        val muscleMap2 = LinkedHashMap<ConstraintLayout, TextView>()
        muscleMap2.put(bodyHistoryBarMuscle2, muscleLabel2)
        val fatMap2 = LinkedHashMap<ConstraintLayout, TextView>()
        fatMap2.put(bodyHistoryBarFat2, fatLabel2)
        val barMap2 = LinkedHashMap<LinkedHashMap<ConstraintLayout, TextView>, LinkedHashMap<ConstraintLayout, TextView>>()
        barMap2.put(muscleMap2, fatMap2)
        val bodyContainerMap2 = LinkedHashMap<LinkedHashMap<LinkedHashMap<ConstraintLayout, TextView>, LinkedHashMap<ConstraintLayout, TextView>>, ConstraintLayout>()
        bodyContainerMap2.put(barMap2, bodyHistoryBarMood2)
        // 3
        val muscleMap3 = LinkedHashMap<ConstraintLayout, TextView>()
        muscleMap3.put(bodyHistoryBarMuscle3, muscleLabel3)
        val fatMap3 = LinkedHashMap<ConstraintLayout, TextView>()
        fatMap3.put(bodyHistoryBarFat3, fatLabel3)
        val barMap3 = LinkedHashMap<LinkedHashMap<ConstraintLayout, TextView>, LinkedHashMap<ConstraintLayout, TextView>>()
        barMap3.put(muscleMap3, fatMap3)
        val bodyContainerMap3 = LinkedHashMap<LinkedHashMap<LinkedHashMap<ConstraintLayout, TextView>, LinkedHashMap<ConstraintLayout, TextView>>, ConstraintLayout>()
        bodyContainerMap3.put(barMap3, bodyHistoryBarMood3)
        // 4
        val muscleMap4 = LinkedHashMap<ConstraintLayout, TextView>()
        muscleMap4.put(bodyHistoryBarMuscle4, muscleLabel4)
        val fatMap4 = LinkedHashMap<ConstraintLayout, TextView>()
        fatMap4.put(bodyHistoryBarFat4, fatLabel4)
        val barMap4 = LinkedHashMap<LinkedHashMap<ConstraintLayout, TextView>, LinkedHashMap<ConstraintLayout, TextView>>()
        barMap4.put(muscleMap4, fatMap4)
        val bodyContainerMap4 = LinkedHashMap<LinkedHashMap<LinkedHashMap<ConstraintLayout, TextView>, LinkedHashMap<ConstraintLayout, TextView>>, ConstraintLayout>()
        bodyContainerMap4.put(barMap4, bodyHistoryBarMood4)
        // 5
        val muscleMap5 = LinkedHashMap<ConstraintLayout, TextView>()
        muscleMap5.put(bodyHistoryBarMuscle5, muscleLabel5)
        val fatMap5 = LinkedHashMap<ConstraintLayout, TextView>()
        fatMap5.put(bodyHistoryBarFat5, fatLabel5)
        val barMap5 = LinkedHashMap<LinkedHashMap<ConstraintLayout, TextView>, LinkedHashMap<ConstraintLayout, TextView>>()
        barMap5.put(muscleMap5, fatMap5)
        val bodyContainerMap5 = LinkedHashMap<LinkedHashMap<LinkedHashMap<ConstraintLayout, TextView>, LinkedHashMap<ConstraintLayout, TextView>>, ConstraintLayout>()
        bodyContainerMap5.put(barMap5, bodyHistoryBarMood5)
        // 6
        val muscleMap6 = LinkedHashMap<ConstraintLayout, TextView>()
        muscleMap6.put(bodyHistoryBarMuscle6, muscleLabel6)
        val fatMap6 = LinkedHashMap<ConstraintLayout, TextView>()
        fatMap6.put(bodyHistoryBarFat6, fatLabel6)
        val barMap6 = LinkedHashMap<LinkedHashMap<ConstraintLayout, TextView>, LinkedHashMap<ConstraintLayout, TextView>>()
        barMap6.put(muscleMap6, fatMap6)
        val bodyContainerMap6 = LinkedHashMap<LinkedHashMap<LinkedHashMap<ConstraintLayout, TextView>, LinkedHashMap<ConstraintLayout, TextView>>, ConstraintLayout>()
        bodyContainerMap6.put(barMap6, bodyHistoryBarMood6)
        // 7
        val muscleMap7 = LinkedHashMap<ConstraintLayout, TextView>()
        muscleMap7.put(bodyHistoryBarMuscle7, muscleLabel7)
        val fatMap7 = LinkedHashMap<ConstraintLayout, TextView>()
        fatMap7.put(bodyHistoryBarFat7, fatLabel7)
        val barMap7 = LinkedHashMap<LinkedHashMap<ConstraintLayout, TextView>, LinkedHashMap<ConstraintLayout, TextView>>()
        barMap7.put(muscleMap7, fatMap7)
        val bodyContainerMap7 = LinkedHashMap<LinkedHashMap<LinkedHashMap<ConstraintLayout, TextView>, LinkedHashMap<ConstraintLayout, TextView>>, ConstraintLayout>()
        bodyContainerMap7.put(barMap7, bodyHistoryBarMood7)
        // 8
        val muscleMap8 = LinkedHashMap<ConstraintLayout, TextView>()
        muscleMap8.put(bodyHistoryBarMuscle8, muscleLabel8)
        val fatMap8 = LinkedHashMap<ConstraintLayout, TextView>()
        fatMap8.put(bodyHistoryBarFat8, fatLabel8)
        val barMap8 = LinkedHashMap<LinkedHashMap<ConstraintLayout, TextView>, LinkedHashMap<ConstraintLayout, TextView>>()
        barMap8.put(muscleMap8, fatMap8)
        val bodyContainerMap8 = LinkedHashMap<LinkedHashMap<LinkedHashMap<ConstraintLayout, TextView>, LinkedHashMap<ConstraintLayout, TextView>>, ConstraintLayout>()
        bodyContainerMap8.put(barMap8, bodyHistoryBarMood8)

        val barContainerMap = LinkedHashMap<ConstraintLayout, LinkedHashMap<LinkedHashMap<LinkedHashMap<ConstraintLayout, TextView>, LinkedHashMap<ConstraintLayout, TextView>>, ConstraintLayout>>()
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
        currentView = view
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