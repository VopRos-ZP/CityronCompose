package ru.cityron.presentation.screens.controller.http

sealed interface ControllerHttpViewIntent {
    data object Launch : ControllerHttpViewIntent
    data object OnSaveClick : ControllerHttpViewIntent
    data object OnSnackbarDismiss : ControllerHttpViewIntent
    data object OnVisibilityP1Change : ControllerHttpViewIntent
    data object OnVisibilityP2Change : ControllerHttpViewIntent
    data class OnFP1Change(val value: Int) : ControllerHttpViewIntent
    data class OnFP2Change(val value: Int) : ControllerHttpViewIntent
    data class OnP1Change(val value: String) : ControllerHttpViewIntent
    data class OnP2Change(val value: String) : ControllerHttpViewIntent
}