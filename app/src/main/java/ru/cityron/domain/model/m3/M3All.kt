package ru.cityron.domain.model.m3

import kotlinx.serialization.Serializable

@Serializable
data class M3All(
    val static: M3Static,
    val settings: M3Settings,
    val sched: M3Sched,
    val state: M3State
)
