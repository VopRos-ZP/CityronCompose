package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Alarm(
    val i: Int = 0,
    val action: Int,
    val delay: Int,
    val en: Int,
    val value: Int
)
