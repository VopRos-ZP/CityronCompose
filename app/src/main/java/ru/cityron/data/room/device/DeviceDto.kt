package ru.cityron.data.room.device

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.cityron.data.room.all.Table

@Entity(tableName = "devices")
data class DeviceDto(
    @PrimaryKey
    val id: Int = Table.ID,
    val deviceId: String,
    val deviceName: String
)
