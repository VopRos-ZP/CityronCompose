package ru.cityron.presentation.screens.metrics

import android.view.View
import androidx.compose.foundation.gestures.TransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
    val state by viewModel.state().collectAsState()

    val transformableState = remember {
        TransformableState { zoomChange, _, _ ->
            // Изменение диапазона времени на основе масштабирования
            val currentRange = state.end - state.start
            val newRange = (currentRange / zoomChange).toLong()

            // Определение новых значений start и end с учетом границ
            val midPoint = state.start + currentRange / 2
            val newStart = (midPoint - newRange / 2).coerceAtLeast(state.minStart)
            val newEnd = (midPoint + newRange / 2).coerceAtMost(state.maxEnd)

            // Обновляем состояние ViewModel
            viewModel.intent(MetricsViewIntent.OnStartChange(newStart))
            viewModel.intent(MetricsViewIntent.OnEndChange(newEnd))
        }
    }
    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .transformable(transformableState),
        factory = { View.inflate(it, R.layout.chart_view, null) },
        update = {
            with(it.findViewById<HIChartView>(R.id.chartView)) {
                if (state.chart != null) {
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

                        val dataWithDate = state.chart!!.channel.mapIndexed { i, temp ->
                            arrayOf(
                                state.chart!!.start + i * (state.chart!!.end - state.chart!!.start) / state.chart!!.point,
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