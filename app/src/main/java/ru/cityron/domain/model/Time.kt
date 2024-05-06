package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Time(
    val fSntp: Int = 0,
    val ip: String = "",
    val zone: Int = 3
)