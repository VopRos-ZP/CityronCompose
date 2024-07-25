package ru.cityron.presentation.screens.algo.fan2

sealed interface AlgoFan2ViewIntent {
    data object Launch : AlgoFan2ViewIntent
    data class OnSpeedMinChange(val value: Int) : AlgoFan2ViewIntent
    data class OnSpeedMaxChange(val value: Int) : AlgoFan2ViewIntent
    data object OnSaveClick : AlgoFan2ViewIntent
}