package ru.cityron.presentation.screens.algo.fan1

data class AlgoFan1ViewState(
    val fan1SpeedMinOld: Int = 20,
    val fan1SpeedMin: Int = fan1SpeedMinOld,
    val fan1SpeedMinMin: Int = 1,
    val fan1SpeedMinMax: Int = 90,
    val fan1SpeedMinInRange: Boolean = true,

    val fan1SpeedMaxOld: Int = 100,
    val fan1SpeedMax: Int = fan1SpeedMaxOld,
    val fan1SpeedMaxMin: Int = 20,
    val fan1SpeedMaxMax: Int = 100,
    val fan1SpeedMaxInRange: Boolean = true,

    val isChanged: Boolean = false,
)
