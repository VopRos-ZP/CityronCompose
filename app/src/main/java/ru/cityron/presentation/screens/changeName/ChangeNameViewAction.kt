package ru.cityron.presentation.screens.changeName

import ru.cityron.presentation.mvi.SnackbarResult

sealed interface ChangeNameViewAction {
    data class ShowSnackbar(val result: SnackbarResult) : ChangeNameViewAction
}