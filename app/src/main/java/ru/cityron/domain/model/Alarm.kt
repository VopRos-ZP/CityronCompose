package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Alarm(
    val i: Int = 0,
    val action: Int = 0,
    val delay: Int = 0,
    val en: Int = 0,
    val value: Int = 0
)
