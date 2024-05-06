package ru.cityron.presentation.screens.metrics

import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.highsoft.highcharts.common.hichartsclasses.HIChart
import com.highsoft.highcharts.common.hichartsclasses.HIColumn
import com.highsoft.highcharts.common.hichartsclasses.HIOptions
import com.highsoft.highcharts.common.hichartsclasses.HITitle
import com.highsoft.highcharts.core.HIChartView
import ru.cityron.R
import java.util.Collections

@Composable
fun MetricsScreen(
    viewModel: MetricsViewModel = hiltViewModel()
) {
    val chart by viewModel.chart.collectAsState()
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { View.inflate(it, R.layout.chart_view, null) },
        update = {
            with(it.findViewById<HIChartView>(R.id.chartView)) {
                val options = HIOptions().apply {
                    this.chart = HIChart().apply { type = "line" }

                    val title = HITitle()
                    title.text = "Demo chart"
                    this.title = title

                    val series = HIColumn()
                    series.data = ArrayList(chart?.channel ?: emptyList())
                    this.series = ArrayList(Collections.singletonList(series))
                }
                this.options = options
            }
        }
    )
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchChart(types = 1, sources = -1, values = 1)
    }
}