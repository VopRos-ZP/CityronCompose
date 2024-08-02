package ru.cityron.presentation.screens.controller

data class ControllerSettingsViewState(
    val rtcTime: Long = 0,
    val zone: Int = 0,
    val timeSntp: Int = 0,
    val ipLoc: String = "",
    val ethDhcp: Int = 0,
    val httpP1: String = "",
    val httpP2: String = "",
    val metricVal: Int = 0,
    val metricFrequency: Int = 6,
)
