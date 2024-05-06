package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Mb(
    val addr: Int = 0,
    val delay: Int = 0,
    val fRtu: Int = 0,
    val fTcp: Int = 0,
    val parity: Int = 0,
    val speed: Int = 0
)