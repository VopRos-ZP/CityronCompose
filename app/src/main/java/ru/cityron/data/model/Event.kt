package ru.cityron.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val data: List<List<Int>>,
    val zone: Int
)
