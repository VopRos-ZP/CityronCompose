package com.vopros.cityron.m3.domain

import kotlinx.serialization.Serializable

@Serializable
data class Srv(
    val connect: Int,
    val isBind: Int,
    val metricAddr: String
)