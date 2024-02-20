package com.vopros.cityron.m3.domain

data class State(
    val alarms: Int,
    val algo: Algo,
    val crcSched: String,
    val crcSettings: String,
    val ipLoc: String,
    val rtcTime: Int,
    val `set`: Set,
    val srv: Srv,
    val ul: Int,
    val uptime: Int
)