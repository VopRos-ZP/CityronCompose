package ru.cityron.presentation.screens.editAlarm

sealed interface EditAlarmViewIntent {
    data object OnSaveClick : EditAlarmViewIntent
    data class Launch(val id: Int) : EditAlarmViewIntent
    data class OnActionChange(val value: Int) : EditAlarmViewIntent
    data class OnDelayChange(val value: Int) : EditAlarmViewIntent
    data class OnValueChange(val value: Int) : EditAlarmViewIntent
}