package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Http(
    val fP1: Int = 0,
    val fP2: Int = 0,
    val p1: String = "",
    val p2: String = ""
)