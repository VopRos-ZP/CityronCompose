package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class JsonSettings<S>(
    val settings: S
)
