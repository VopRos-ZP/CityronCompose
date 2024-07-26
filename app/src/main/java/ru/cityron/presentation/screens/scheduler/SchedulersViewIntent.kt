package ru.cityron.presentation.screens.scheduler

sealed interface SchedulersViewIntent {
    data object Launch : SchedulersViewIntent
    data class OnCheckedChange(
        val sched: Int,
        val value: Int
    ) : SchedulersViewIntent
}