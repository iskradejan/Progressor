package com.progressor.progressor.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.progressor.progressor.R
import com.progressor.progressor.di.components.MainComponentInterface
import com.progressor.progressor.presenter.BodyHistoryPresenter
import kotlinx.android.synthetic.main.layout_body_history.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class BodyHistoryFragment : BaseFragment(), BodyHistoryPresenter.View {

    @Inject
    lateinit var presenter: BodyHistoryPresenter
    private var dateLabels = ArrayList<TextView>()

    private fun initialize() {
        setDateLabelArray()
        setDates()
    }

    private fun setDateLabelArray() {
        dateLabels = arrayListOf<TextView>(bodyHistoryAxisYLabel1, bodyHistoryAxisYLabel2, bodyHistoryAxisYLabel3, bodyHistoryAxisYLabel4, bodyHistoryAxisYLabel5, bodyHistoryAxisYLabel6, bodyHistoryAxisYLabel7, bodyHistoryAxisYLabel8)
    }

    private fun setDates() {
        presenter.getBodyList()?.forEachIndexed { index, body ->
            dateLabels[index].text = convertDate(body.createDate)
        }
    }

    private fun convertDate(localDateTime: String): String {
        val fromFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)
        val toFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)
        val date = fromFormat.parse(localDateTime)
        return toFormat.format(date)
    }

    override fun totalDateLabels(): Int {
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