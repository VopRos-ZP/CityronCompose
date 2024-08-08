package ru.cityron.presentation.screens.controller.datetime

import ru.cityron.presentation.mvi.SnackbarResult

sealed interface ControllerDatetimeViewAction {
    data class ShowSnackbar(val result: SnackbarResult) : ControllerDatetimeViewAction
}