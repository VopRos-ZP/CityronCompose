package ru.cityron.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Http(
    val fP1: Int,
    val fP2: Int,
    val p1: String,
    val p2: String
)