package ru.cityron.presentation.screens.editScheduler

interface EditSchedulerViewIntent {
    data object OnSaveClick : EditSchedulerViewIntent
    data object OnSnackbarDismiss : EditSchedulerViewIntent
    data class Launch(val value: Int) : EditSchedulerViewIntent
    data class OnHourChange(val value: Int) : EditSchedulerViewIntent
    data class OnMinChange(val value: Int) : EditSchedulerViewIntent
    data class OnDayChange(val value: Int) : EditSchedulerViewIntent
    data class OnModeChange(val value: Int) : EditSchedulerViewIntent
    data class OnFanChange(val value: Int) : EditSchedulerViewIntent
    data class OnTempChange(val value: Int) : EditSchedulerViewIntent
    data class OnPowerChange(val value: Int) : EditSchedulerViewIntent
}