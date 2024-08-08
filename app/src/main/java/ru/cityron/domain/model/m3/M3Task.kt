package ru.cityron.domain.model.m3

import kotlinx.serialization.Serializable

@Serializable
data class M3Task(
    val i: Int = 0,
    val day: Int = 0,
    val fan: Int = 1,
    val hour: Int = 0,
    val min: Int = 0,
    val mode: Int = 0,
    val on: Int = 0,
    val power: Int = 0,
    val temp: Int = 22
)