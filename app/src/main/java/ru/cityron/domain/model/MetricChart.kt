package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MetricChart(
    val chart: Chart
)
