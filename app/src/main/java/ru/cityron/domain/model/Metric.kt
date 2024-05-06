package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Metric(
    val frequency: Int = 0,
    val valuesBits: Int = 0
)