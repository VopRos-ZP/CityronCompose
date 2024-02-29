package com.vopros.cityron.m3.domain

import kotlinx.serialization.Serializable

@Serializable
data class Set(
    val fan: Int = 0,
    val mode: Int = 0,
    val power: Int = 0,
    val sched: Int = 0,
    val temp: Int = 50
)