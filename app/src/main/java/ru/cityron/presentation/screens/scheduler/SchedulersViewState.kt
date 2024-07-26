package ru.cityron.presentation.screens.scheduler

import ru.cityron.domain.model.m3.M3Task

data class SchedulersViewState(
    val tasks: List<M3Task> = emptyList(),
)