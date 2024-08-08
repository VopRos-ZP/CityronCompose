package ru.cityron.presentation.screens.metrics

import ru.cityron.domain.model.Chart

data class MetricsViewState(
    val minStart: Long = 1592311220L,
    val start: Long = minStart,
    val maxEnd: Long = 1598400585L,
    val end: Long = maxEnd,
    val chart: Chart? = null,
)
