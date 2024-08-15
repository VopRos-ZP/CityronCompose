package ru.cityron.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BindsAddResponse(
    val result: String,
    val num: Int
)
