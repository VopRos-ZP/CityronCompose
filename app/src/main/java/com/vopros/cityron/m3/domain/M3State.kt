package com.vopros.cityron.m3.domain

import kotlinx.serialization.Serializable

@Serializable
data class M3State(
    val state: State = State()
)