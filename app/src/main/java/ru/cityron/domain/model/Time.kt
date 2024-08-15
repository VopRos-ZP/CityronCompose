package ru.cityron.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Time(
    val fSntp: Int = 0,
    val ip: String = "",
    val zone: Int = 3,
    @Transient val zoneMin: Int = -14,
    @Transient val zoneMax: Int = 14
)