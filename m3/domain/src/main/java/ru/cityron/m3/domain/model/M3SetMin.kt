package ru.cityron.m3.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class M3SetMin(
    val fan: Int,
    val mode: Int,
    val power: Int,
    val sched: Int,
    val temp: Int
)