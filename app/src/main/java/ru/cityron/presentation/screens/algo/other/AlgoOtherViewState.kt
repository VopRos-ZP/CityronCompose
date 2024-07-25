package ru.cityron.presentation.screens.algo.other

data class AlgoOtherViewState(
    val tempControlOld: Int,
    val tempControl: Int,

    val filterEnOld: Int,
    val filterEn: Int,

    val autoStartEnOld: Int,
    val autoStartEn: Int,

    val isDistPowerOld: Int,
    val isDistPower: Int,

    val alarmRestartCountOld: Int,
    val alarmRestartCount: Int,

    val isChanged: Boolean = false
)
