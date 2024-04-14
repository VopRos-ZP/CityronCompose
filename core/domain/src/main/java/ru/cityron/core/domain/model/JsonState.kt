package ru.cityron.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class JsonState<S>(
    val state: S
)