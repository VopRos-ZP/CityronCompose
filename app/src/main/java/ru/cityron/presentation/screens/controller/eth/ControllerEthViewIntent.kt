package ru.cityron.presentation.screens.controller.eth

sealed interface ControllerEthViewIntent {
    data object Launch : ControllerEthViewIntent
    data object OnSaveClick : ControllerEthViewIntent
    data object OnSnackbarDismiss : ControllerEthViewIntent
    data class OnFDhcpChange(val value: Int) : ControllerEthViewIntent
    data class OnIpChange(val value: String) : ControllerEthViewIntent
    data class OnMaskChange(val value: String) : ControllerEthViewIntent
    data class OnGwChange(val value: String) : ControllerEthViewIntent
    data class OnMacChange(val value: String) : ControllerEthViewIntent
}