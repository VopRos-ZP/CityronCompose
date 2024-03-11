package ru.cityron.network

import kotlinx.serialization.Serializable

@Serializable
data class Events(
    val `data`: List<List<Int>>,
    val zone: Int
)