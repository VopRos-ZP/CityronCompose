package ru.cityron.presentation.screens.editScheduler

import ru.cityron.presentation.mvi.SnackbarResult

interface EditSchedulerViewAction {
    data class ShowSnackbar(val result: SnackbarResult) : EditSchedulerViewAction
}