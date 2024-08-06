package ru.cityron.presentation.screens.addCustom

import ru.cityron.presentation.mvi.SnackbarResult

sealed interface AddCustomViewIntent {
    data object Launch : AddCustomViewIntent
    data object OnNextClick : AddCustomViewIntent
    data class OnIpChange(val value: String) : AddCustomViewIntent
    data class OnSnackbarResultChange(val value: SnackbarResult?) : AddCustomViewIntent
}