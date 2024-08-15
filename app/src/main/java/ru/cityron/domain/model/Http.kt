package ru.cityron.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Http(
    val fPr: Int = 0,
    val fPu: Int = 0,
    val fPw: Int = 0,
    @SerialName("Pr")
    val pr: String = "",
    @SerialName("Pu")
    val pu: String = "",
    @SerialName("Pw")
    val pw: String = "",
)