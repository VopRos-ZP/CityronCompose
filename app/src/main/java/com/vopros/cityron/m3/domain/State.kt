package com.vopros.cityron.m3.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class State(
    val alarms: Int,
    val algo: Algo,
    val crcSched: String,
    val crcSettings: String,
    val ipLoc: String,
    val rtcTime: Int,
    @SerialName("set")
    val `set`: Set,
    val srv: Srv,
    val ul: Int,
    val uptime: Int
)