package ru.cityron.presentation.screens.algo.water

sealed interface AlgoWaterViewIntent {
    data object Launch : AlgoWaterViewIntent
    data object OnSaveClick : AlgoWaterViewIntent
    data class OnModeZimaLetoSourceChange(val value: Int) : AlgoWaterViewIntent
    data class OnModeZimaLetoUserChange(val value: Int) : AlgoWaterViewIntent
    data class OnTimeWarmUpChange(val value: Int) : AlgoWaterViewIntent
    data class OnTimeDefrostChange(val value: Int) : AlgoWaterViewIntent
}