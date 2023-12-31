package com.tonkeeper.fragment.chart.list.holder

import android.view.ViewGroup
import com.tonapps.tonkeeperx.R
import com.tonkeeper.api.chart.ChartPeriod
import com.tonkeeper.fragment.chart.list.ChartItem
import com.tonkeeper.view.ChartView

class ChartLineHolder(
    parent: ViewGroup
): ChartHolder<ChartItem.Chart>(parent, R.layout.view_chart_line) {

    private val chartView = findViewById<ChartView>(R.id.chart)

    override fun onBind(item: ChartItem.Chart) {
        chartView.setData(item.data, item.period == ChartPeriod.hour)
    }

}