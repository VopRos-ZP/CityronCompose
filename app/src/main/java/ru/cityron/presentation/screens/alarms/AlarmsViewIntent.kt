package ru.cityron.presentation.screens.alarms

import ru.cityron.domain.model.Alarm

interface AlarmsViewIntent {
    data class OnAlarmEnChange(val alarm: Alarm, val value: Boolean) : AlarmsViewIntent
}