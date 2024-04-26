package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class KeyError(
    val error: String,
    val key: String
)
