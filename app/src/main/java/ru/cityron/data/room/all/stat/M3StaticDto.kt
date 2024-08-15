package ru.cityron.data.room.all.stat

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.cityron.data.room.all.Table

@Entity(tableName = "m3_static")
data class M3StaticDto(
    @PrimaryKey
    val id: Int = Table.ID,
    val devName: String = "",
    val idCpu: String = "",
    val idUsr: String = "",
)
