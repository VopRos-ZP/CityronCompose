package ru.cityron.presentation.screens.metrics

sealed interface MetricsViewIntent {
    data class FetchChart(
        val start: Long,
        val end: Long,
        val types: Int,
        val sources: Int,
        val values: Int
    ) : MetricsViewIntent

    data class OnStartChange(val value: Long) : MetricsViewIntent
    data class OnEndChange(val value: Long) : MetricsViewIntent
}