package ru.cityron.presentation.screens.controller.metric

import ru.cityron.presentation.mvi.SnackbarResult

sealed interface ControllerMetricViewAction {
    data class ShowSnackbar(val result: SnackbarResult) : ControllerMetricViewAction
}