package ru.cityron.presentation.screens.addCustom

import ru.cityron.presentation.mvi.SnackbarResult

sealed interface AddCustomViewAction {
    data class Snackbar(val result: SnackbarResult) : AddCustomViewAction
}