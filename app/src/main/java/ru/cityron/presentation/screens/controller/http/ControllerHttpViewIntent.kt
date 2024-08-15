package ru.cityron.presentation.screens.controller.http

sealed interface ControllerHttpViewIntent {
    data object Launch : ControllerHttpViewIntent
    data object OnSaveClick : ControllerHttpViewIntent
    data object OnSnackbarDismiss : ControllerHttpViewIntent
    data object OnVisibilityPrChange : ControllerHttpViewIntent
    data object OnVisibilityPuChange : ControllerHttpViewIntent
    data object OnVisibilityPwChange : ControllerHttpViewIntent
    data class OnFPrChange(val value: Int) : ControllerHttpViewIntent
    data class OnFPuChange(val value: Int) : ControllerHttpViewIntent
    data class OnFPwChange(val value: Int) : ControllerHttpViewIntent
    data class OnPrChange(val value: String) : ControllerHttpViewIntent
    data class OnPuChange(val value: String) : ControllerHttpViewIntent
    data class OnPwChange(val value: String) : ControllerHttpViewIntent
}