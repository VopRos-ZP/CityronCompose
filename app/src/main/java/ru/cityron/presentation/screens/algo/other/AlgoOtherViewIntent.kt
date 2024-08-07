package ru.cityron.presentation.screens.algo.other

sealed interface AlgoOtherViewIntent {
    data object Launch : AlgoOtherViewIntent
    data object OnSaveClick : AlgoOtherViewIntent
    data object OnSnackbarDismiss : AlgoOtherViewIntent
    data class OnTempControlChange(val value: Int) : AlgoOtherViewIntent
    data class OnFilterEnChange(val value: Int) : AlgoOtherViewIntent
    data class OnAutoStartEnChange(val value: Int) : AlgoOtherViewIntent
    data class OnIsDistPowerChange(val value: Int) : AlgoOtherViewIntent
    data class OnAlarmRestartCountChange(val value: Int) : AlgoOtherViewIntent
}