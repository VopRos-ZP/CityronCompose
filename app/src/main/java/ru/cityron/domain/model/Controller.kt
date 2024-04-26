package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Controller(
    val id: Int,
    val name: String,
    val ipAddress: String,
    val idCpu: String
)