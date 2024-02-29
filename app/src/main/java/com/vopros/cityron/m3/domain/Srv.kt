package com.vopros.cityron.m3.domain

import kotlinx.serialization.Serializable

@Serializable
data class Srv(
    val connect: Int = 0,
    val isBind: Int = 0,
    val metricAddr: String = ""
)