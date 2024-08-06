package ru.cityron.presentation.screens.addCustom

import ru.cityron.presentation.mvi.SnackbarResult

data class AddCustomViewState(
    val ip: String = "",
    val isCorrect: Boolean = false,
    val result: SnackbarResult? = null
)
