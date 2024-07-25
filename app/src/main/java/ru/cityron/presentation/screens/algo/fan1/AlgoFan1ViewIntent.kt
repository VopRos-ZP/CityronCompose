package ru.cityron.presentation.screens.algo.fan1

sealed interface AlgoFan1ViewIntent {
    data object Launch : AlgoFan1ViewIntent
    data class OnSpeedMinChange(val value: Int) : AlgoFan1ViewIntent
    data class OnSpeedMaxChange(val value: Int) : AlgoFan1ViewIntent
    data object OnSaveClick : AlgoFan1ViewIntent
}