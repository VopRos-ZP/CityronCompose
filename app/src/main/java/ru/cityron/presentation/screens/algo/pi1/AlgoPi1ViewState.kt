package ru.cityron.presentation.screens.algo.pi1

data class AlgoPi1ViewState(
    val piAutoEnOld: Int = 0,
    val piAutoEn: Int = piAutoEnOld,

    val piKofPOld: Int = 33,
    val piKofP: Int = piKofPOld,
    val piKofPRange: IntRange = (0..255),
    val piKofPInRange: Boolean = true,

    val piKofIOld: Int = 100,
    val piKofI: Int = piKofIOld,
    val piKofIRange: IntRange = (0..255),
    val piKofIInRange: Boolean = true,

    val piErrOld: Int = 0,
    val piErr: Int = piErrOld,
    val piErrRange: IntRange = (0..255),
    val piErrInRange: Boolean = true,

    val isChanged: Boolean = false,
)
