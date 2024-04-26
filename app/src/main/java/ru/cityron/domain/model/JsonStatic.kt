package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class JsonStatic<S>(
    val static: S
)
