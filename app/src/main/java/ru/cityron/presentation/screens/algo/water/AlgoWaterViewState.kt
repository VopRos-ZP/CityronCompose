package ru.cityron.presentation.screens.algo.water

data class AlgoWaterViewState(
    val modeZimaLetoSourceOld: Int = 1,
    val modeZimaLetoSource: Int = modeZimaLetoSourceOld,

    val modeZimaLetoUserOld: Int = 0,
    val modeZimaLetoUser: Int = modeZimaLetoUserOld,

    val timeWarmUpOld: Int = 3,
    val timeWarmUp: Int = timeWarmUpOld,
    val timeWarmUpRange: IntRange = (0..255),
    val timeWarmUpInRange: Boolean = true,

    val timeDefrostOld: Int = 3,
    val timeDefrost: Int = timeDefrostOld,
    val timeDefrostRange: IntRange = (0..255),
    val timeDefrostInRange: Boolean = true,

    val isChanged: Boolean = false,
)