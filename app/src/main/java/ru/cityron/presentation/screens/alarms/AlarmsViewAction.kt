package ru.cityron.presentation.screens.alarms

sealed interface AlarmsViewAction {
    data class OnNavigate(val id: Int) : AlarmsViewAction
}