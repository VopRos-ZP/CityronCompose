package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Chart(
    val channel: List<Int?>,
    val end: Int,
    val events: List<List<Int>>,
    val point: Int,
    val start: Int,
    val zone: Int
)