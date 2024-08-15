package ru.cityron.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Alarm(
    val i: Int = 0,
    val action: Int = 0,
    val delay: Int = 0,
    val en: Int = 0,
    val value: Int = 0,
    @Transient val delayMin: Int = 0,
    @Transient val delayMax: Int = 0,
    @Transient val valueMin: Int = 0,
    @Transient val valueMax: Int = 0,
)
