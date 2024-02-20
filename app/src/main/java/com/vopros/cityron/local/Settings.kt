package com.cityron.domain.model.local

import com.vopros.cityron.local.Ids
import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    val uuid: String = "",
    val tokens: List<Ids> = emptyList(),
)