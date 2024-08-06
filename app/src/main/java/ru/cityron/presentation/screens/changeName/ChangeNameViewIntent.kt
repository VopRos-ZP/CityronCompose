package ru.cityron.presentation.screens.changeName

import ru.cityron.presentation.mvi.SnackbarResult

sealed interface ChangeNameViewIntent {
    data object Launch : ChangeNameViewIntent
    data object OnSaveClick : ChangeNameViewIntent
    data class OnNameChange(val value: String) : ChangeNameViewIntent
    data class OnSnakbarResultChange(val value: SnackbarResult?) : ChangeNameViewIntent
}