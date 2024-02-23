package com.vopros.cityron.m3.domain

import kotlinx.serialization.Serializable

@Serializable
data class Set(
    val fan: Int,
    val mode: Int,
    val power: Int,
    val sched: Int,
    val temp: Int
)