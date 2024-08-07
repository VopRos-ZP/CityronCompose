package ru.cityron.presentation.screens.editAlarm

import ru.cityron.presentation.mvi.SnackbarResult

sealed interface EditAlarmViewAction {
    data class ShowSnackbar(val result: SnackbarResult) : EditAlarmViewAction
}