package com.vopros.cityron.m3.domain

import kotlinx.serialization.Serializable

@Serializable
data class Algo(
    val modeZimaLeto: Int,
    val status: Int,
    val statusSec: Int,
    val tempBWater: Int,
    val tempExt: Int,
    val tempPv: Int,
    val type: Int
)