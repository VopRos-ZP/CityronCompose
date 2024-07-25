package ru.cityron.presentation.screens.algo.pi2

data class AlgoPi2ViewState(
    val pi2KofPOld: Int,
    val pi2KofP: Int,
    val pi2KofIOld: Int,
    val pi2KofI: Int,
    val pi2ErrOld: Int,
    val pi2Err: Int,
    val isChanged: Boolean = false,
)
