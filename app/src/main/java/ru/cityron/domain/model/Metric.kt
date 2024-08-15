package ru.cityron.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Metric(
    val frequency: Int = 0,
    val valuesBits: Int = 0,
    @Transient val frequencyMin: Int = 1,
    @Transient val frequencyMax: Int = 7,
    @Transient val valuesBitsMin: Int = 0,
    @Transient val valuesBitsMax: Int = 255,
)