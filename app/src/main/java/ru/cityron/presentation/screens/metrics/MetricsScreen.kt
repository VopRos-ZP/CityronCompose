package ru.cityron.presentation.screens.metrics

import android.view.View
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
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
import com.highsoft.highcharts.common.hichartsclasses.HILabels
import com.highsoft.highcharts.common.hichartsclasses.HIOptions
import com.highsoft.highcharts.common.hichartsclasses.HISeries
import com.highsoft.highcharts.common.hichartsclasses.HITitle
import com.highsoft.highcharts.common.hichartsclasses.HIXAxis
import com.highsoft.highcharts.common.hichartsclasses.HIYAxis
import com.highsoft.highcharts.core.HIChartView
import ru.cityron.R
import ru.cityron.domain.utils.Temp
import java.util.Collections

@Composable
fun MetricsScreen(
    viewModel: MetricsViewModel = hiltViewModel()
) {
    val chart by viewModel.chart.collectAsState()
    val transformableState =  TransformableState { zoomChange, _, _ ->

    }
    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .transformable(transformableState),
        factory = { View.inflate(it, R.layout.chart_view, null) },
        update = {
            with(it.findViewById<HIChartView>(R.id.chartView)) {
                if (chart != null) {
                    val options = HIOptions().apply {
                        this.chart = HIChart().apply {
                            type = "line"
                        }

                        this.xAxis = arrayListOf(
                            HIXAxis().apply {
                                type = "datetime"
                                minRange = 3e5
                            }
                        )
                        this.yAxis = arrayListOf(
                            HIYAxis().apply {
                                minRange = 10
                                title = HITitle().apply {
                                    isEnabled = true
                                }
                                labels = HILabels().apply {
                                    format = "{value}°C"
                                }
                            }
                        )

                        val title = HITitle()
                        title.text = ""
                        this.title = title

                        val dataWithDate = chart!!.channel.mapIndexed { i, temp ->
                            arrayOf(
                                i * (chart!!.end - chart!!.start / chart!!.point) + chart!!.start,
                                when (temp) {
                                    null -> null
                                    else -> Temp.toGrade(temp).toDouble()
                                }
                            )
                        }

                        val series = HISeries().apply {
                            data = ArrayList(dataWithDate)
                        }
                        this.series = ArrayList(Collections.singletonList(series))
                    }
                    this.options = options
                }
            }
        }
    )
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchChart(types = 1, sources = -1, values = 1)
    }
}