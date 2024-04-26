package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SrvMin(
    val connect: Int = 0,
    val isBind: Int = 0,
    val metricAddr: String = ""
)
