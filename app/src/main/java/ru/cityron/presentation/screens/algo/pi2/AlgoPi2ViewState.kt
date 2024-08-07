package ru.cityron.presentation.screens.algo.pi2

data class AlgoPi2ViewState(
    val pi2KofPOld: Int = 33,
    val pi2KofP: Int = pi2KofPOld,
    val pi2KofPRange: IntRange = (0..255),
    val pi2KofPInRange: Boolean = true,

    val pi2KofIOld: Int = 100,
    val pi2KofI: Int = pi2KofIOld,
    val pi2KofIRange: IntRange = (0..255),
    val pi2KofIInRange: Boolean = true,

    val pi2ErrOld: Int = 0,
    val pi2Err: Int = pi2ErrOld,
    val pi2ErrRange: IntRange = (0..255),
    val pi2ErrInRange: Boolean = true,

    val isChanged: Boolean = false,
)
