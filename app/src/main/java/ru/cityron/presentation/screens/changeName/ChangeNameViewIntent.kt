package ru.cityron.presentation.screens.changeName

sealed interface ChangeNameViewIntent {
    data object Launch : ChangeNameViewIntent
    data object OnSaveClick : ChangeNameViewIntent
    data object OnSnackbarDismiss : ChangeNameViewIntent
    data class OnNameChange(val value: String) : ChangeNameViewIntent
}