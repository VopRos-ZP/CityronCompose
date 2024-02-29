package com.vopros.cityron.domain.events

import kotlinx.serialization.Serializable

@Serializable
data class Events(
    val `data`: List<List<Int>>,
    val zone: Int
)