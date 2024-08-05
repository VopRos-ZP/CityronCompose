package ru.cityron.presentation.screens.controller.http

data class ControllerHttpViewState(
    val fP1: Int = 0,
    val visibilityP1: Boolean = false,
    val fP2: Int = 0,
    val visibilityP2: Boolean = false,

    val p1Old: String = "",
    val p1: String = p1Old,

    val p2Old: String = "",
    val p2: String = p2Old,

    val isChanged: Boolean = false,
)
