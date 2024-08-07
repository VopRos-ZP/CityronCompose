package ru.cityron.presentation.screens.algo.other

data class AlgoOtherViewState(
    val tempControlOld: Int = 0,
    val tempControl: Int = tempControlOld,

    val filterEnOld: Int = 1,
    val filterEn: Int = filterEnOld,

    val autoStartEnOld: Int = 1,
    val autoStartEn: Int = autoStartEnOld,

    val isDistPowerOld: Int = 0,
    val isDistPower: Int = isDistPowerOld,

    val alarmRestartCountOld: Int = 5,
    val alarmRestartCount: Int = alarmRestartCountOld,
    val alarmRestartCountRange: IntRange = (0..10),
    val alarmRestartCountInRange: Boolean = alarmRestartCountOld in alarmRestartCountRange,

    val isChanged: Boolean = false
)
