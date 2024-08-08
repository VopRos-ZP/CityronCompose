package ru.cityron.presentation.screens.editScheduler

data class EditSchedulerViewState(
    val id: Int = 0,

    val hourOld: Int = 0,
    val hour: Int = hourOld,
    val hourValues: List<Int> = emptyList(),

    val minOld: Int = 0,
    val min: Int = minOld,
    val minValues: List<Int> = emptyList(),

    val dayOld: Int = 0,
    val day: Int = dayOld,

    val modeOld: Int = 0,
    val mode: Int = modeOld,

    val fanOld: Int = 1,
    val fan: Int = fanOld,

    val tempOld: Int = 22,
    val temp: Int = tempOld,
    val tempValues: List<Int> = emptyList(),

    val powerOld: Int = 0,
    val power: Int = powerOld,

    val isChanged: Boolean = false,
)
