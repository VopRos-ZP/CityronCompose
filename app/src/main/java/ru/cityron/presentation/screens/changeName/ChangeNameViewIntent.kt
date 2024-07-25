package ru.cityron.presentation.screens.changeName

sealed interface ChangeNameViewIntent {
    data object Launch : ChangeNameViewIntent
    data object OnSaveClick : ChangeNameViewIntent
    data class OnNameChange(val value: String) : ChangeNameViewIntent
    data class OnIsShowSnakbarChange(val value: Boolean) : ChangeNameViewIntent
    data class OnIsErrorCheckedChange(val value: Boolean?) : ChangeNameViewIntent
}