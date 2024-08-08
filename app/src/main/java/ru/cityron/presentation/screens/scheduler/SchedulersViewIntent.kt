package ru.cityron.presentation.screens.scheduler

sealed interface SchedulersViewIntent {
    data class OnCheckedChange(
        val sched: Int,
        val value: Int
    ) : SchedulersViewIntent
}