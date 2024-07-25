package ru.cityron.presentation.screens.algo.electric

data class AlgoElectricViewState(
    val heatPwmPeriodOld: Int,
    val heatPwmPeriod: Int,
    val isChanged: Boolean = false
)
