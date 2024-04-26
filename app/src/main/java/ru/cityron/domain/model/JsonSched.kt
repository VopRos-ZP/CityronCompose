package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class JsonSched<S>(
    val sched: S
)
