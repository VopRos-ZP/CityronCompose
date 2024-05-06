package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Chart(
    val channel: List<Int?> = emptyList(),
    val end: Int = 0,
    val events: List<List<Int>> = emptyList(),
    val point: Int = 0,
    val start: Int = 0,
    val zone: Int = 3
)