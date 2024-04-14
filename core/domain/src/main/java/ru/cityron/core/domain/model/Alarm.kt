package ru.cityron.core.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Alarm(
    @Transient
    val i: Int = 0,
    val action: Int,
    val delay: Int,
    val en: Int,
    val value: Int
)
