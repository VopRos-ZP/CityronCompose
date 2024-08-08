package ru.cityron.presentation.screens.controller.metric

data class ControllerMetricViewState(
    val valuesBitsOld: Int = 1,
    val valuesBits: Int = valuesBitsOld,

    val frequencyOld: Int = 2,
    val frequency: Int = frequencyOld,

    val capacity: Int = 62,

    val isChanged: Boolean = false,
)
