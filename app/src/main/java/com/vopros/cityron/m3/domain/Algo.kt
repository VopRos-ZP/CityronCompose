package com.vopros.cityron.m3.domain

import kotlinx.serialization.Serializable

@Serializable
data class Algo(
    val modeZimaLeto: Int = 0,
    val status: Int = 0,
    val statusSec: Int = 0,
    val tempBWater: Int = 0,
    val tempExt: Int = 0,
    val tempPv: Int = 0,
    val type: Int = 0
)