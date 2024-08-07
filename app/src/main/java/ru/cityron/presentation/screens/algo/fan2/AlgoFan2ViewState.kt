package ru.cityron.presentation.screens.algo.fan2

data class AlgoFan2ViewState(
    val fan2SpeedMinOld: Int = 20,
    val fan2SpeedMin: Int = fan2SpeedMinOld,
    val fan2SpeedMinMin: Int = 1,
    val fan2SpeedMinMax: Int = 90,
    val fan2SpeedMinInRange: Boolean = true,

    val fan2SpeedMaxOld: Int = 100,
    val fan2SpeedMax: Int = fan2SpeedMaxOld,
    val fan2SpeedMaxMin: Int = 20,
    val fan2SpeedMaxMax: Int = 100,
    val fan2SpeedMaxInRange: Boolean = true,

    val isChanged: Boolean = false,
)
