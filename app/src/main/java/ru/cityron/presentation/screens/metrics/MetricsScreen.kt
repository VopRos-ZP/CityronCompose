package ru.cityron.presentation.screens.metrics

import android.icu.util.TimeZone
import android.view.View
import android.widget.LinearLayout
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.RangeSlider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.highsoft.highcharts.common.HIColor
import com.highsoft.highcharts.common.hichartsclasses.HIChart
import com.highsoft.highcharts.common.hichartsclasses.HIEvents
import com.highsoft.highcharts.common.hichartsclasses.HILabels
import com.highsoft.highcharts.common.hichartsclasses.HILegend
import com.highsoft.highcharts.common.hichartsclasses.HILine
import com.highsoft.highcharts.common.hichartsclasses.HIOptions
import com.highsoft.highcharts.common.hichartsclasses.HIPoint
import com.highsoft.highcharts.common.hichartsclasses.HISeries
import com.highsoft.highcharts.common.hichartsclasses.HISpline
import com.highsoft.highcharts.common.hichartsclasses.HITitle
import com.highsoft.highcharts.common.hichartsclasses.HIXAxis
import com.highsoft.highcharts.common.hichartsclasses.HIYAxis
import com.highsoft.highcharts.core.HIChartView
import ru.cityron.R
import ru.cityron.domain.utils.Temp
import ru.cityron.domain.utils.toTimeZone
import java.util.Collections

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MetricsScreen(
    viewModel: MetricsViewModel = hiltViewModel()
) {
    val state by viewModel.state().collectAsState()

    var innerRange by remember { mutableStateOf((state.start.toFloat()..state.end.toFloat())) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            modifier = Modifier.weight(1f),
            factory = { View.inflate(it, R.layout.chart_view, null) },
            update = {
                when (val chart = state.chart) {
                    null -> {}
                    else -> it.findViewById<HIChartView>(R.id.chartView).apply {
                        val timeZone = TimeZone.getTimeZone("GMT${chart.zone.toTimeZone()}")
                        val offset = timeZone.rawOffset

                        val start = chart.start * 1000L
                        val end = chart.end * 1000L
                        val point = chart.point

                        val interval = (end - start) / point

                        val channel: MutableList<Array<Number?>?> = ArrayList()
                        for (i in 0..<point) {
                            val time = start + i * interval + offset
                            val temp = try {
                                chart.channel[i]
                            } catch (_: Exception) {
                                null
                            }

                            channel.add(
                                arrayOf(
                                    time,
                                    when (temp) {
                                        null -> null
                                        else -> Temp.toGrade(temp).toDouble()
                                    }
                                )
                            )
                        }

                        val options = HIOptions().apply {
                            this.chart = HIChart().apply {
                                type = "line"
                            }

                            this.xAxis = arrayListOf(
                                HIXAxis().apply {
                                    type = "datetime"
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

                            val series = HISeries().apply {
                                this.name = "Температура в канале"
                                this.data = ArrayList(channel)
                            }
                            this.series = ArrayList(Collections.singletonList(series))
                        }

//                            val events = HISpline()
//                            events.name = "Аварии"
//                            events.data = arrayListOf(
//                                arrayOf(1715765000L * 1000 + offset, "A"),
//                                arrayOf(1715880000L * 1000 + offset, "4"),
//                                arrayOf(1716000000L * 1000 + offset, "2")
//                            )
//                            //events.onSeries = "series"
//                            //events.shape = "squarepin"
//                            events.color = HIColor.initWithName("red")
//                            options.series.add(events)

                        // Настройка легенды
                        val legend = HILegend()
                        legend.enabled = true
                        options.legend = legend
                        this.options = options
                    }
                }
            }
        )
        RangeSlider(
            value = (state.start.toFloat()..state.end.toFloat()),
            onValueChange = { innerRange = it },
            onValueChangeFinished = {
                viewModel.intent(MetricsViewIntent.OnStartChange(innerRange.start.toLong()))
                viewModel.intent(MetricsViewIntent.OnEndChange(innerRange.endInclusive.toLong()))
            },
            valueRange = (state.minStart.toFloat()..state.maxEnd.toFloat())
        )
    }
    LaunchedEffect(state.start, state.end) {
        viewModel.intent(MetricsViewIntent.FetchChart(
            start = state.start,
            end = state.end,
            types = 1,
            sources = -1,
            values = 1
        ))
    }
}