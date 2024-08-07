package ru.cityron.presentation.screens.algo.electric

interface AlgoElectricViewIntent {
    data object Launch : AlgoElectricViewIntent
    data object OnSaveClick : AlgoElectricViewIntent
    data object OnSnackbarDismiss : AlgoElectricViewIntent
    data class OnHeatPwmPeriodChange(val value: Int) : AlgoElectricViewIntent
}