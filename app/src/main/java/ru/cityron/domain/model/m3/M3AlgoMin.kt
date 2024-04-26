package ru.cityron.domain.model.m3

import kotlinx.serialization.Serializable

@Serializable
data class M3AlgoMin(
    val modeZimaLeto: Int = 0,
    val status: Int = 0,
    val statusSec: Int = 0,
    val tempBWater: Int = 0,
    val tempExt: Int = 50,
    val tempPv: Int = 50,
    val type: Int = 0
)