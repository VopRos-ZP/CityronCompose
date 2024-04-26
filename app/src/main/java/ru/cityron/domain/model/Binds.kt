package ru.cityron.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Binds(
    @SerialName("00")
    val zero: Bind,
    @SerialName("01")
    val one: Bind,
    @SerialName("02")
    val two: Bind,
    @SerialName("03")
    val three: Bind,
)
