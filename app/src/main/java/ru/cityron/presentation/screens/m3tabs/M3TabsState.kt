package ru.cityron.presentation.screens.m3tabs

import ru.cityron.domain.model.m3.M3State

data class M3TabsState(
    val state: M3State? = null,
    val error: String? = null
)
