package ru.cityron.core.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "controller")
data class ControllerEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("ip_address")
    val ipAddress: String,
    @ColumnInfo("id_cpu")
    val idCpu: String
)
