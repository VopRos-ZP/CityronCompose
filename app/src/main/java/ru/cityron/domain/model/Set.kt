package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Set(
    val fan: Int = 1,
    val mode: Int = 0,
    val power: Int = 0,
    val sched: Int = 0,
    val temp: Int = 50
)
