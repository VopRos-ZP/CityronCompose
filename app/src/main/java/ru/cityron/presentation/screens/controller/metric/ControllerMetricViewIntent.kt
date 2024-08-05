package ru.cityron.presentation.screens.controller.metric

sealed interface ControllerMetricViewIntent {
    data object Launch : ControllerMetricViewIntent
    data object OnSaveClick : ControllerMetricViewIntent
    data class OnValuesBitsChange(val value: Int) : ControllerMetricViewIntent
    data class OnFrequencyChange(val value: Int) : ControllerMetricViewIntent
}