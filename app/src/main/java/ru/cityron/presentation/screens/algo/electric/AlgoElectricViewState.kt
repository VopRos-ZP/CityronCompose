package ru.cityron.presentation.screens.algo.electric

data class AlgoElectricViewState(
    val heatPwmPeriodOld: Int = 30,
    val heatPwmPeriod: Int = heatPwmPeriodOld,
    val min: Int = 2,
    val max: Int = 60,
    val isInRange: Boolean = true,
    val isChanged: Boolean = false
)
