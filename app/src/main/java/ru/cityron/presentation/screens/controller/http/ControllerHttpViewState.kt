package ru.cityron.presentation.screens.controller.http

data class ControllerHttpViewState(
    val fPr: Int = 0,
    val visibilityPr: Boolean = false,
    val fPu: Int = 0,
    val visibilityPu: Boolean = false,
    val fPw: Int = 0,
    val visibilityPw: Boolean = false,

    val prOld: String = "",
    val pr: String = prOld,

    val puOld: String = "",
    val pu: String = puOld,

    val pwOld: String = "",
    val pw: String = pwOld,

    val isChanged: Boolean = false,
)
