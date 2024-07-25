package ru.cityron.presentation.screens.editAlarm

import ru.cityron.domain.model.Alarm

data class EditAlarmViewState(
    val alarm: Alarm? = null,
    val localAlarm: Alarm? = null,
    val isChanged: Boolean = alarm != localAlarm,
    val delayValues: List<Int> = emptyList(),
    val valueValues: List<Int> = emptyList(),
)
