package ru.cityron.presentation.screens.controller.datetime

sealed interface ControllerDatetimeViewIntent {
    data object Launch : ControllerDatetimeViewIntent
    data object OnSaveClick : ControllerDatetimeViewIntent
    data object OnSnackbarDismiss : ControllerDatetimeViewIntent
    data class OnTimeFSntpChange(val value: Int) : ControllerDatetimeViewIntent
    data class OnTimeIpChange(val value: String) : ControllerDatetimeViewIntent
    data class OnDateChange(val value: String) : ControllerDatetimeViewIntent
    data class OnTimeChange(val value: String) : ControllerDatetimeViewIntent
    data class OnTimeZoneChange(val value: Int) : ControllerDatetimeViewIntent
}