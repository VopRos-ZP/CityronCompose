package ru.cityron.data.room.controller

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "controllers")
data class ControllerDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    @ColumnInfo(name = "ip_address")
    val ipAddress: String,
    @ColumnInfo(name = "id_cpu")
    val idCpu: String,
    @ColumnInfo(name = "id_usr")
    val idUsr: String,
    val status: String,
    val num: String,
    val token: String?
)