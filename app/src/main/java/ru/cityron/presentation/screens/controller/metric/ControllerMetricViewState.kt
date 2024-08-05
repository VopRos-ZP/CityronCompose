package ru.cityron.presentation.screens.controller.metric

data class ControllerMetricViewState(
    val valuesBitsOld: Int = 0,
    val valuesBits: Int = 0,

    val frequencyOld: Int = 0,
    val frequency: Int = 0,

    val capacity: Int = 62,

    val isChanged: Boolean = false,
)
