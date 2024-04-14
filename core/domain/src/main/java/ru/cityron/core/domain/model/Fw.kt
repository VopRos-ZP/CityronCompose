package ru.cityron.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Fw(
    val altname: String,
    val len: Int,
    val md5: String,
    val name: String,
    val ver: Int
)

