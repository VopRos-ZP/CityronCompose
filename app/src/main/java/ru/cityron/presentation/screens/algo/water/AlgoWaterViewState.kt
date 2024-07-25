package ru.cityron.presentation.screens.algo.water

data class AlgoWaterViewState(
    val modeZimaLetoSourceOld: Int,
    val modeZimaLetoSource: Int,

    val modeZimaLetoUserOld: Int,
    val modeZimaLetoUser: Int,

    val timeWarmUpOld: Int,
    val timeWarmUp: Int,

    val timeDefrostOld: Int,
    val timeDefrost: Int,

    val isChanged: Boolean = false,
)