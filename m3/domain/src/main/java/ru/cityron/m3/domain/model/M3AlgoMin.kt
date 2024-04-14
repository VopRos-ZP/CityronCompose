package ru.cityron.m3.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class M3AlgoMin(
    val modeZimaLeto: Int,
    val status: Int,
    val statusSec: Int,
    val tempBWater: Int,
    val tempExt: Int,
    val tempPv: Int,
    val type: Int
)