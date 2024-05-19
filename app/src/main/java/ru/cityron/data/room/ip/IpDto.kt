package ru.cityron.data.room.ip

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ips")
data class IpDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val address: String
)
