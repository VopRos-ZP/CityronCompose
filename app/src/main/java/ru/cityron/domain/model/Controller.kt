package ru.cityron.domain.model

import kotlinx.serialization.Serializable
import ru.cityron.data.room.all.Table

@Serializable
data class Controller(
    val id: Int = Table.ID,
    val name: String,
    val ipAddress: String,
    val idCpu: String,
    val idUsr: String,
    val status: Status
)