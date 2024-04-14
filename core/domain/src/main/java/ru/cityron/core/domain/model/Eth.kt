package ru.cityron.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Eth(
    val fDhcp: Int,
    val gw: String,
    val ip: String,
    val mac2: String,
    val mask: String
)