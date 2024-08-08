package ru.cityron.presentation.screens.editAlarm

data class EditAlarmViewState(
    val i: Int = 1,
    val actionOld: Int = 1,
    val action: Int = actionOld,

    val delayOld: Int = 10,
    val delay: Int = delayOld,
    val delayValues: List<Int> = emptyList(),

    val valueOld: Int = 45,
    val value: Int = valueOld,
    val valueValues: List<Int> = emptyList(),

    val isChanged: Boolean = false,
)
