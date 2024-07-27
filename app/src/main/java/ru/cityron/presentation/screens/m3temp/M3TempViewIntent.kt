package ru.cityron.presentation.screens.m3temp

sealed interface M3TempViewIntent {
    data object Launch : M3TempViewIntent
    data object OnConfirmOnOffClick : M3TempViewIntent
    data class OnTempChange(val value: Int) : M3TempViewIntent
    data class OnFanChange(val value: Int) : M3TempViewIntent
    data class OnIsShowOnOffDialogChange(val value: Boolean) : M3TempViewIntent
}