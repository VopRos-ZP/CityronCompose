package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Srv(
    val fEn: Int = 0,
    val fMetric: Int = 0,
    val fUser: Int = 0,
    val fUserIp: String = "0.0.0.0"
)