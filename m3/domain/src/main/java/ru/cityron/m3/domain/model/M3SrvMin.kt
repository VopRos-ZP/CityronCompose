package ru.cityron.m3.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class M3SrvMin(
    val connect: Int,
    val isBind: Int,
    val metricAddr: String
)