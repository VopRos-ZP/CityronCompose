package ru.cityron.presentation.screens.algo.fan1

data class AlgoFan1ViewState(
    val fan1SpeedMinOld: Int,
    val fan1SpeedMin: Int,
    val fan1SpeedMaxOld: Int,
    val fan1SpeedMax: Int,
    val isChanged: Boolean = false,
)
