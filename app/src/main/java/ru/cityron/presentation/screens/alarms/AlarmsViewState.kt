package ru.cityron.presentation.screens.alarms

import ru.cityron.domain.model.Alarm

data class AlarmsViewState(val alarms: List<Alarm> = emptyList())
