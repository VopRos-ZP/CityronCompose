package ru.cityron.presentation.screens.editAlarm

import ru.cityron.presentation.mvi.SnackbarResult

data class EditAlarmViewState(
    val i: Int,
    val actionOld: Int = 0,
    val action: Int = actionOld,

    val delayOld: Int = 0,
    val delay: Int = delayOld,

    val valueOld: Int = 0,
    val value: Int = valueOld,

    val isChanged: Boolean = false,
    val delayValues: List<Int> = emptyList(),
    val valueValues: List<Int> = emptyList(),

    val result: SnackbarResult? = null,
)
