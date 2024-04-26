package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Metric(
    val frequency: Int,
    val valuesBits: Int
)