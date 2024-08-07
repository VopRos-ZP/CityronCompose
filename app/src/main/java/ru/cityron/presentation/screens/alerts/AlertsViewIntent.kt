package ru.cityron.presentation.screens.alerts

sealed interface AlertsViewIntent {
    data class OnAlertsArrayChange(val value: List<String>) : AlertsViewIntent
}