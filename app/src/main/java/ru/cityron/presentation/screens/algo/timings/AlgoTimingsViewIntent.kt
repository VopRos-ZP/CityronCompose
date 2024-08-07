package ru.cityron.presentation.screens.algo.timings

sealed interface AlgoTimingsViewIntent {
    data object Launch  : AlgoTimingsViewIntent
    data object OnSaveClick : AlgoTimingsViewIntent
    data object OnSnackbarDismiss : AlgoTimingsViewIntent
    data class OnTimeOpenDamperChange(val value: Int) : AlgoTimingsViewIntent
    data class OnTimeAccelerFanChange(val value: Int) : AlgoTimingsViewIntent
    data class OnTimeBlowHeatChange(val value: Int) : AlgoTimingsViewIntent
}