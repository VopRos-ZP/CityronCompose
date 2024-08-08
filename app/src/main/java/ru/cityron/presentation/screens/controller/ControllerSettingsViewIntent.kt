package ru.cityron.presentation.screens.controller

sealed interface ControllerSettingsViewIntent {
    data object Launch : ControllerSettingsViewIntent
    data object OnConfirmDeleteClick : ControllerSettingsViewIntent
    data class OnIsShowDeleteDialogChange(val value: Boolean) : ControllerSettingsViewIntent
}