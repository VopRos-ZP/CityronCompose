package ru.cityron.presentation.screens.algo.pi1

data class AlgoPi1ViewState(
    val piAutoEnOld: Int,
    val piAutoEn: Int,
    val piKofPOld: Int,
    val piKofP: Int,
    val piKofIOld: Int,
    val piKofI: Int,
    val piErrOld: Int,
    val piErr: Int,
    val isChanged: Boolean = false,
)
