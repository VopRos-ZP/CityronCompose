package ru.cityron.presentation.screens.algo.fan2

data class AlgoFan2ViewState(
    val fan2SpeedMinOld: Int,
    val fan2SpeedMin: Int,
    val fan2SpeedMaxOld: Int,
    val fan2SpeedMax: Int,
    val isChanged: Boolean = false,
)
