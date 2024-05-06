package ru.cityron.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Others(
    val fDebug: Int = 0,
    val fTest: Int = 0,
    val loc: String = "",
    val ntcFilter: String = ""
)