package ru.cityron.m3.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class M3All(
    val static: M3Static,
    val settings: M3Settings,
    val sched: M3Sched,
    val state: M3State
)
