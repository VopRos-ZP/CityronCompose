package ru.cityron.presentation.screens.controller

sealed interface ControllerSettingsViewIntent {
    data object Launch : ControllerSettingsViewIntent
    data object OnDeleteControllerClick : ControllerSettingsViewIntent
}