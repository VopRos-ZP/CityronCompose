package ru.cityron.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Srv(
    val fEn: Int,
    val fMetric: Int,
    val fUser: Int,
    val fUserIp: String = "0.0.0.0"
)