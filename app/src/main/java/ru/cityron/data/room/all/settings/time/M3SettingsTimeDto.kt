package ru.cityron.data.room.all.settings.time

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.cityron.data.room.all.Table

@Entity(tableName = "m3_settings_time")
data class M3SettingsTimeDto(
    @PrimaryKey
    val id: Int = Table.ID,
    @ColumnInfo(name = "f_sntp")
    val fSntp: Int = 0,
    val ip: String = "",
    val zone: Int = 3,
    @ColumnInfo(name = "zone_min")
    val zoneMin: Int = -14,
    @ColumnInfo(name = "zone_max")
    val zoneMax: Int = 14
)
