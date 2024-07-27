package ru.cityron.presentation.screens.m3temp

data class M3TempViewState(
    val temp: Int = 50,
    val tempPv: Int = 50,
    val fan: Int = 1,
    val isShowAlarms: Boolean = false,
    val isShowOnOffDialog: Boolean = false,
    val isPowerOn: Boolean = false,
)
