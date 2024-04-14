package ru.cityron.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Mb(
    val addr: Int,
    val delay: Int,
    val fRtu: Int,
    val fTcp: Int,
    val parity: Int,
    val speed: Int
)