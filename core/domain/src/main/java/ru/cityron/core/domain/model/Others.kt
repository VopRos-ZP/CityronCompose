package ru.cityron.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Others(
    val fDebug: Int,
    val fTest: Int,
    val loc: String,
    val ntcFilter: String = ""
)