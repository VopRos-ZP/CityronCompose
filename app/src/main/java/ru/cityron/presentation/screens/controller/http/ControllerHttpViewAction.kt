package ru.cityron.presentation.screens.controller.http

import ru.cityron.presentation.mvi.SnackbarResult

sealed interface ControllerHttpViewAction {
    data class ShowSnackbar(val result: SnackbarResult) : ControllerHttpViewAction
}