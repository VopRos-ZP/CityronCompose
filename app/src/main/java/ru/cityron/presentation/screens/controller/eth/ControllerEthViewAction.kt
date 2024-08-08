package ru.cityron.presentation.screens.controller.eth

import ru.cityron.presentation.mvi.SnackbarResult

sealed interface ControllerEthViewAction {
    data class ShowSnackbar(val result: SnackbarResult) : ControllerEthViewAction
}