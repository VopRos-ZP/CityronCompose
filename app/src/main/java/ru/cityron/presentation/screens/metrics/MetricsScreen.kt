package ru.cityron.presentation.screens.metrics

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.highsoft.highcharts.core.HIChartView

@Composable
fun MetricsScreen() {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { HIChartView(it) }
    )
}