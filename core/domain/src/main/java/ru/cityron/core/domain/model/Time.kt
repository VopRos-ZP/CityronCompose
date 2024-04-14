package ru.cityron.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Time(
    val fSntp: Int,
    val ip: String,
    val zone: Int
)