package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Bind(
    val access: Int,
    val info: String
)
