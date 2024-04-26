package ru.cityron.domain.model.m3

import kotlinx.serialization.Serializable

@Serializable
data class M3Task(
    val i: Int = 0,
    val day: Int,
    val fan: Int,
    val hour: Int,
    val min: Int,
    val mode: Int,
    val on: Int,
    val power: Int,
    val temp: Int
)