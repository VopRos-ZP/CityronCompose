package com.vopros.cityron.m3.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class State(
    val alarms: Int = 0,
    val algo: Algo = Algo(),
    val crcSched: String = "",
    val crcSettings: String = "",
    val ipLoc: String = "",
    val rtcTime: Int = 0,
    @SerialName("set")
    val `set`: Set = Set(),
    val srv: Srv = Srv(),
    val ul: Int = 0,
    val uptime: Int = 0
)