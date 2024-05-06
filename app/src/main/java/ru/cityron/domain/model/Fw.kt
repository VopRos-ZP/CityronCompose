package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Fw(
    val altname: String = "",
    val len: Int = 0,
    val md5: String = "",
    val name: String = "",
    val ver: Int = 0
)

